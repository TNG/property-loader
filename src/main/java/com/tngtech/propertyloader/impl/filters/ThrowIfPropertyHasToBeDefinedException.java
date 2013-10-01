package com.tngtech.propertyloader.impl.filters;


import com.tngtech.propertyloader.exception.PropertyLoaderException;

public class ThrowIfPropertyHasToBeDefinedException extends PropertyLoaderException {
    public ThrowIfPropertyHasToBeDefinedException(String message) {
        super(message);
    }

    public ThrowIfPropertyHasToBeDefinedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThrowIfPropertyHasToBeDefinedException(Throwable cause) {
        super(cause);
    }
}
