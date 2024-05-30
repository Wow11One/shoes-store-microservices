package com.ukma.mail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Address;
import com.ukma.mail.dto.MailDto;
import com.ukma.mail.service.MailService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQConsumer;
import io.vertx.rabbitmq.RabbitMQOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainVerticle extends AbstractVerticle {

  private final String RABBITMQ_HOST = "rabbitmq";
  private final Integer PORT = 5672;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public void rabbitMqSetup(Vertx vertx) {
    RabbitMQOptions config = new RabbitMQOptions();
    config.setConnectionTimeout(10000);
    config.setAddresses(List.of(new Address(RABBITMQ_HOST, PORT)));
    config.setReconnectAttempts(10);
    RabbitMQClient client = RabbitMQClient.create(vertx, config);
    client
      .start()
      .timeout(100, TimeUnit.SECONDS)
      .onComplete(asyncResult -> {
        if (asyncResult.succeeded()) {
          System.out.println("RabbitMQ successfully connected!");

          client.basicConsumer("mail.queue", rabbitMQConsumerAsyncResult -> {

            if (rabbitMQConsumerAsyncResult.succeeded()) {
              System.out.println("RabbitMQ consumer created !");

              RabbitMQConsumer mqConsumer = rabbitMQConsumerAsyncResult.result();
              mqConsumer.handler(message -> {
                System.out.println("Got message: " + message.body().toString());
                try {
                  MailDto mailDto = objectMapper.readValue(message.body().toString(), MailDto.class);
                  MailService mailService = new MailService(vertx);
                  mailService.sendEmail(mailDto);
                } catch (JsonProcessingException e) {
                  throw new RuntimeException(e);
                }
              });
            } else {
              System.out.println(rabbitMQConsumerAsyncResult.cause());
            }
          });

        } else {
          System.out.println("Fail to connect to RabbitMQ " + asyncResult.cause().getMessage());
        }
      });
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.createHttpServer().requestHandler(request -> {
      HttpServerResponse response = request.response();
      response.putHeader("content-type", "text/plain");
      response.end("Hello World!");
    }).listen(8888, http -> {
      if (http.succeeded()) {
        rabbitMqSetup(vertx);
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
