package com.tngtech.propertyloader.impl.interfaces;


import java.net.URL;

public interface PropertyLocationsContainer<T> {
    public T atDefaultLocations();

    public T atCurrentDirectory();

    public T atHomeDirectory();

    public T atDirectory(String directory);

    public T atContextClassPath();

    public T atRelativeToClass(Class<?> reference);

    public T atClassLoader(ClassLoader classLoader);

    public T atBaseURL(URL url);
}
