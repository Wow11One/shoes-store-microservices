package com.ukma.controller.exception.handler;

import com.ukma.dto.exception.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto globalHandler(Exception exception) {
        return ExceptionDto.builder()
                .message(exception.getMessage())
                .build();
    }
}
