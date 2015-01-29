package com.tngtech.propertyloader.impl.interfaces;

import java.net.URL;

public interface PropertyLocationsContainer<T> {

    T atDefaultLocations();

    T atCurrentDirectory();

    T atHomeDirectory();

    T atDirectory(String directory);

    T atContextClassPath();

    T atRelativeToClass(Class<?> reference);

    T atClassLoader(ClassLoader classLoader);

    T atBaseURL(URL url);
}
