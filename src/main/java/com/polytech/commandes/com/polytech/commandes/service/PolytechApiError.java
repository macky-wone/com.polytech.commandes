package com.polytech.commandes.com.polytech.commandes.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Exception wrapper used across services to return an HTTP status and message.
 */
public class PolytechApiError extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public PolytechApiError(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public PolytechApiError(String message) {
        this(HttpStatus.BAD_REQUEST, message);
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ResponseEntity<String> getResponse() {
        return ResponseEntity.status(status).body(message);
    }
}