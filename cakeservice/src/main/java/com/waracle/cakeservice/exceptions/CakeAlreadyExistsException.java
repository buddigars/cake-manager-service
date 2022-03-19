package com.waracle.cakeservice.exceptions;

public class CakeAlreadyExistsException extends RuntimeException {
    final String message;

    public CakeAlreadyExistsException(String message) {
        this.message = message;
    }
}
