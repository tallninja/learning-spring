package com.tallninja.socialapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiExceptionHandler(ApiRequestException e) {
        // create the payload
        ApiExceptionEntity apiExceptionEntity = new ApiExceptionEntity(
                e.getStatus(),
                e.getLocalizedMessage(),
                ZonedDateTime.now(ZoneId.of("UTC"))
        );

        // return the response
        return new ResponseEntity<>(apiExceptionEntity, e.getStatus());
    }

}
