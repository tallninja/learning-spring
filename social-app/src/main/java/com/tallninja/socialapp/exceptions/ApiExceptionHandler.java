package com.tallninja.socialapp.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiExceptionEntity apiExceptionEntity = new ApiExceptionEntity(
                HttpStatus.BAD_REQUEST,
                "Data validation failed",
                ZonedDateTime.now(ZoneId.of("UTC"))
        );
        List<FieldError> fieldErrors = ex.getFieldErrors();
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        apiExceptionEntity.setErrors(errors);

        return new ResponseEntity<>(apiExceptionEntity, apiExceptionEntity.getStatus());
    }
}
