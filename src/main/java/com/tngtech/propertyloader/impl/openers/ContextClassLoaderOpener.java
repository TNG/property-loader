package com.tngtech.propertyloader.impl.openers;

import com.tngtech.propertyloader.impl.PropertyLoaderOpener;

import java.io.InputStream;

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
