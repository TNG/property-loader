package com.tngtech.propertyloader.impl.openers;

import com.tngtech.propertyloader.impl.PropertyLoaderOpener;

import java.io.InputStream;

public class RelativeToClass implements PropertyLoaderOpener {
    private final Class<?> reference;

    public RelativeToClass(Class<?> reference) {
        this.reference = reference;
    }

    public InputStream open(String filename) {
        return reference.getResourceAsStream(filename);
    }

    @Override
    public String toString() {
        return "near " + reference;
    }
}
