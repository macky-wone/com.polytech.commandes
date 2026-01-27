package com.polytech.commandes.com.polytech.commandes.exception;

public class StockInsuffisantException extends RuntimeException {

    public StockInsuffisantException(String message) {
        super(message);
    }

    public StockInsuffisantException(String message, Throwable cause) {
        super(message, cause);
    }
}
