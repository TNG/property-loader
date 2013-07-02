package com.tngtech.propertyloader.impl.openers;

import java.io.InputStream;

import com.tngtech.propertyloader.impl.PropertyLoaderOpener;

class ContextClassLoaderOpener implements PropertyLoaderOpener {
    public InputStream open(String filename) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        return contextClassLoader.getResourceAsStream(filename);
    }

    @Override
    public String toString() {
        return "classpath";
    }
}
