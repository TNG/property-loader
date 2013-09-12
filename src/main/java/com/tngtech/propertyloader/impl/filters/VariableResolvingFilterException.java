package com.tngtech.propertyloader.impl.filters;


import com.tngtech.propertyloader.exception.PropertyLoaderException;

public class VariableResolvingFilterException extends PropertyLoaderException{
    public VariableResolvingFilterException(String message) {
        super(message);
    }

    public VariableResolvingFilterException(String message, Throwable cause) {
        super(message, cause);
    }

    public VariableResolvingFilterException(Throwable cause) {
        super(cause);
    }
}
