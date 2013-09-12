package com.tngtech.propertyloader.impl.openers;


import com.tngtech.propertyloader.exception.PropertyLoaderException;

public class WebOpenerException extends PropertyLoaderException{

    public WebOpenerException(String message) {
        super(message);
    }

    public WebOpenerException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebOpenerException(Throwable cause) {
        super(cause);
    }
}
