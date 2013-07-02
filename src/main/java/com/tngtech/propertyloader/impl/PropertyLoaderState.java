package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.PropertyLoader;

/**
 * Represents a PropertyLoader.load() call.
 *
 * Used in advanced PropertyLoaderFilter classes.
 */
public class PropertyLoaderState {
    private final String basename;
    private final String suffix;
    private final PropertyLoader loader;

    public PropertyLoaderState(String basename, String suffix, PropertyLoader loader) {
        this.basename = basename;
        this.suffix = suffix;
        this.loader = loader;
    }

    public String getBasename() {
        return basename;
    }

    public String getSuffix() {
        return suffix;
    }

    public PropertyLoader getLoader() {
        return loader;
    }
}
