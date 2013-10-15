package com.tngtech.propertyloader.impl.openers;

import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderOpener;

import java.io.InputStream;

/**
 * Searches for properties files relative to the location of a given class.
 */
public class RelativeToClassOpener implements PropertyLoaderOpener {

    private final Class<?> reference;

    public RelativeToClassOpener(Class<?> reference) {
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
