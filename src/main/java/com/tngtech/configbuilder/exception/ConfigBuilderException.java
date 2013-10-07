package com.tngtech.configbuilder.exception;

public class ConfigBuilderException extends RuntimeException {

    public ConfigBuilderException(String message) {
        super(message);
    }

    public ConfigBuilderException(String message, Throwable e) {
        super(message, e);
    }
}
