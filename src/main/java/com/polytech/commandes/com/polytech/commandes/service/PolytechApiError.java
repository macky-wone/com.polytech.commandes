package com.polytech.commandes.com.polytech.commandes.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@Getter
public class PolytechApiError extends RuntimeException {
    private final int code;
    private final String msg;

    @SuppressWarnings("rawtypes")
    public ResponseEntity getResponse() {
        return ResponseEntity.status(code).body(msg);
    }
}
