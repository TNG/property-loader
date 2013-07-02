package com.tngtech.propertyloader.impl.openers;

import java.io.InputStream;

import com.tngtech.propertyloader.impl.PropertyLoaderOpener;

class ClassLoaderOpener implements PropertyLoaderOpener {
    private final ClassLoader classLoader;

    public ClassLoaderOpener(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public InputStream open(String filename) {
        return classLoader.getResourceAsStream(filename);
    }

    @Override
    public String toString() {
        return "classloader " + classLoader;
    }
}
