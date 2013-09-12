package com.tngtech.propertyloader.exception;


public class PropertyLoaderException extends RuntimeException{

    public PropertyLoaderException(String message) {
        super(message);
    }

    public PropertyLoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyLoaderException(Throwable cause) {
        super(cause);
    }
}
