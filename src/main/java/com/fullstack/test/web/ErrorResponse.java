package com.fullstack.test.web;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private HttpStatus status;
    private String message;

    private ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorResponse of(HttpStatus status, String message) {
        return new ErrorResponse(status, message);
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatus() {
        return status.value();
    }
}
