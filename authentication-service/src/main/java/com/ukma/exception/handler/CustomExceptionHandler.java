package com.ukma.exception.handler;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {ExceptionHandler.class })
public class CustomExceptionHandler
        implements ExceptionHandler<Exception, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, Exception exception) {
        return HttpResponse.serverError(exception.getMessage()).
                status(HttpStatus.BAD_REQUEST);
    }
}