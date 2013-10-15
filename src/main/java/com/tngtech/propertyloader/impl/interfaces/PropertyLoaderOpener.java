package com.tngtech.propertyloader.impl.interfaces;

import java.io.InputStream;

/**
 * Implementations of this interface know how to find property files.
 * <p/>
 * Implementations should override toString() to return a short description of where they search,
 * to enable logging output such as "Found CCSWEBApplication.test.properties in system classpath".
 * In that case, "system classpath" would be a reasonable return value of toString().
 */
public interface PropertyLoaderOpener {

    /**
     * Attempt to find and open some property file.
     *
     * @param fileName A relative filename (using '/' as directory separator on all platforms)
     * @return An input stream reading from that file, or null if the file could not be found
     */
    InputStream open(String fileName);
}
