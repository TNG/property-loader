package com.tngtech.propertyloader.impl.openers;

import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderOpener;

import java.io.InputStream;

/**
 * Searches for properties files using the ContextClassLoader from the current thread.
 */
public class ContextClassLoaderOpener implements PropertyLoaderOpener {
    public InputStream open(String fileName) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        return contextClassLoader.getResourceAsStream(fileName);
    }

    @Override
    public String toString() {
        return "in classpath";
    }
}
