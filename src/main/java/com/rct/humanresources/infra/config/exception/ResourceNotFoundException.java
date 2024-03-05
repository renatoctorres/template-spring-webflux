package com.rct.humanresources.infra.config.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String param) {
        super("Resource with param: " + param + " is not found.");
    }

    public ResourceNotFoundException() {
        super("No found any resources");
    }
}

