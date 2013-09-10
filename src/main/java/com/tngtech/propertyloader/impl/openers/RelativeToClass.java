package com.tngtech.propertyloader.impl.openers;

import com.tngtech.propertyloader.impl.PropertyLoaderOpener;

import java.io.InputStream;

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
