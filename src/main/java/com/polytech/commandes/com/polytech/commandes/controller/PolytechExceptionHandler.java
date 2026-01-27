package com.polytech.commandes.com.polytech.commandes.controller;

import com.polytech.commandes.com.polytech.commandes.service.PolytechApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class PolytechExceptionHandler {

    @ExceptionHandler(PolytechApiError.class)
    @ResponseBody
    public ResponseEntity<?> handlePolytechApiError(PolytechApiError ex) {
        return ex.getResponse();
    }
}
