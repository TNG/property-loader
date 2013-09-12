package com.tngtech.propertyloader.impl.openers;


import com.tngtech.propertyloader.impl.PropertyLoaderOpener;

import java.io.InputStream;
import java.net.URLClassLoader;

public class URLClassLoaderOpener implements PropertyLoaderOpener {
    private final URLClassLoader classLoader;

    public URLClassLoaderOpener(URLClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public InputStream open(String fileName) {
        return classLoader.getResourceAsStream(fileName);
    }
}
