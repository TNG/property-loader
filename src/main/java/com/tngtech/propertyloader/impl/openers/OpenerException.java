package com.tngtech.propertyloader.impl.openers;


import com.tngtech.propertyloader.exception.PropertyLoaderException;

public class OpenerException extends PropertyLoaderException{

    public OpenerException(String message) {
        super(message);
    }

    public OpenerException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenerException(Throwable cause) {
        super(cause);
    }
}
