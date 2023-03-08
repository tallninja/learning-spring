package com.tallninja.socialapp.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiExceptionEntity {

    private final HttpStatus status;
    private final int statusCode;
    private final String message;
    private final ZonedDateTime timestamp;

    public ApiExceptionEntity(HttpStatus status, String message, ZonedDateTime timestamp) {
        this.statusCode = status.value();
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getMessage() {
        return this.message;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }
}
