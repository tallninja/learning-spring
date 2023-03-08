package com.tallninja.socialapp.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ApiExceptionEntity {

    private final HttpStatus status;
    private final int statusCode;
    private final String message;
    private List<String> errors;
    private final ZonedDateTime timestamp;

    public ApiExceptionEntity(HttpStatus status, String message, ZonedDateTime timestamp, List<String> errors) {
        this.statusCode = status.value();
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.errors = errors;
    }

    public ApiExceptionEntity(HttpStatus status, String message, ZonedDateTime timestamp) {
        this.statusCode = status.value();
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
