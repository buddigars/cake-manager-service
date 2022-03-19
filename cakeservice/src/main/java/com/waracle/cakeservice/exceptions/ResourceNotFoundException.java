package com.waracle.cakeservice.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    final String resourceType;

    public ResourceNotFoundException(String resourceType) {
        this.resourceType = resourceType;
    }
}
