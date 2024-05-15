package com.ukma.mail.service;

import com.ukma.mail.dto.MailDto;
import io.vertx.core.Vertx;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;
import io.vertx.ext.mail.MailMessage;
import io.vertx.ext.mail.StartTLSOptions;

public class MailService {

  private MailClient mailClient;

  public MailService(Vertx vertx) {
    MailConfig config = new MailConfig();
    config.setHostname("smtp.gmail.com");
    config.setPort(587);
    config.setPassword("rhcz ftgo doyg tiuf");
    config.setUsername("chotkiypaca349@gmail.com");
    config.setStarttls(StartTLSOptions.REQUIRED);
    MailClient mailClient = MailClient.create(vertx, config);
  }

  public void sendEmail(MailDto mailDto) {
    System.out.println(mailDto);
    MailMessage message = new MailMessage();
    message.setFrom("chotkiypaca349@gmail.com");
    message.setTo("vert.x.shoes.mail.service@gmail.com");
    message.setSubject("Notification from vert.x email service");

    mailClient.sendMail(message, result -> {
      if (result.succeeded()) {
        System.out.println(result.result());
      } else {
        result.cause().printStackTrace();
      }
    });
  }


  public String createHtml() {
    String html = "<table>\n" +
      "            <thead>\n" +
      "            <tr>\n" +
      "                <th>Image</th>\n" +
      "                <th>Name</th>\n" +
      "                <th>Amount</th>\n" +
      "                <th>Size</th>\n" +
      "                <th>Remove</th>\n" +
      "                <th>Unit Price</th>\n" +
      "                <th>Total price</th>\n" +
      "            </tr>\n" +
      "            </thead>\n" +
      "            <tbody>\n";



    return "<table>\n" +
      "            <thead>\n" +
      "            <tr>\n" +
      "                <th>Image</th>\n" +
      "                <th>Name</th>\n" +
      "                <th>Amount</th>\n" +
      "                <th>Size</th>\n" +
      "                <th>Remove</th>\n" +
      "                <th>Unit Price</th>\n" +
      "                <th>Total price</th>\n" +
      "            </tr>\n" +
      "            </thead>\n" +
      "            <tbody>\n" +
      "            {order.basket.map(item =>\n" +
      "                <tr key={item.id}>\n" +
      "                    <td><Image\n" +
      "                        width={50}\n" +
      "                        height={50}\n" +
      "                        src={item.image}\n" +
      "                        style={{cursor: 'pointer'}}\n" +
      "                        onClick={() => navigate(SHOES_ROUTE + '/' + item.shoesId)}\n" +
      "                    /></td>\n" +
      "                    <td>{item.name}</td>\n" +
      "                    <td>\n" +
      "                        <FormControl\n" +
      "                            value={item.amount}\n" +
      "                            style={{width: '80%'}}\n" +
      "                            type='number'\n" +
      "                            min='1'\n" +
      "                            max='100'\n" +
      "                            onChange={e => changeAmount(e, item)}/>\n" +
      "                    </td>\n" +
      "                    <td>{item.size}</td>\n" +
      "                    <td><DeleteButton deleteAction={() => deleteFromBasket(item.id)}/></td>\n" +
      "                    <td>{item.price} hrn</td>\n" +
      "                    <td><b>{item.price * item.amount} hrn</b></td>\n" +
      "                </tr>\n" +
      "            )}\n" +
      "            <tr>\n" +
      "                <td colSpan={6}></td>\n" +
      "                <td><b>{order.totalPrice} hrn</b></td>\n" +
      "            </tr>\n" +
      "            </tbody>\n" +
      "        </table>";
  }
}
