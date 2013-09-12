package com.tngtech.propertyloader.impl;


import com.tngtech.propertyloader.exception.PropertyLoaderException;

public class PropertyFileReaderException extends PropertyLoaderException{
    public PropertyFileReaderException(String message) {
        super(message);
    }

    public PropertyFileReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyFileReaderException(Throwable cause) {
        super(cause);
    }
}
