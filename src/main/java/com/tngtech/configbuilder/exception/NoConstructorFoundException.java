package com.tngtech.configbuilder.exception;


public class NoConstructorFoundException extends RuntimeException {
    public NoConstructorFoundException(String message) {
        super(message);
    }
}
