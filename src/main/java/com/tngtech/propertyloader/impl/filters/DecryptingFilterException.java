package com.tngtech.propertyloader.impl.filters;

import com.tngtech.propertyloader.exception.PropertyLoaderException;

public class DecryptingFilterException extends PropertyLoaderException {

    public DecryptingFilterException(String message) {
        super(message);
    }
}
