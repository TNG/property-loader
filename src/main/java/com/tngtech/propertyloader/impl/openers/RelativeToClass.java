package com.tngtech.propertyloader.impl.openers;

import java.io.InputStream;

import com.tngtech.propertyloader.impl.PropertyLoaderOpener;

class RelativeToClass implements PropertyLoaderOpener {
    private final Class<?> reference;

    RelativeToClass(Class<?> reference) {
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
