package com.tngtech.configbuilder;

public class ConfigBuilderException extends RuntimeException {
    public ConfigBuilderException(Throwable e) {
        super(e);
    }

    public ConfigBuilderException(String message) {
        super(message);
    }
}
