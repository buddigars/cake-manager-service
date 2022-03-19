package com.waracle.cakeservice.exceptions;

public class DatabaseOperationException extends RuntimeException {
    final String dbError;

    public DatabaseOperationException(String dbError) {
        this.dbError = dbError;
    }
}
