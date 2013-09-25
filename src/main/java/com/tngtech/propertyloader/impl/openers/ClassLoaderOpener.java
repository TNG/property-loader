package com.tngtech.propertyloader.impl.openers;


import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderOpener;

import java.io.InputStream;

public class ClassLoaderOpener implements PropertyLoaderOpener {
    private final ClassLoader classLoader;

    public ClassLoaderOpener(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public InputStream open(String fileName) {
        return classLoader.getResourceAsStream(fileName);
    }

    @Override
    public String toString() {
        return "by classloader " + classLoader;
    }
}
