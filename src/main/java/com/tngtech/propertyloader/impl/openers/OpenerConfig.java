package com.tngtech.propertyloader.impl.openers;

import java.util.ArrayList;
import java.util.List;

import com.tngtech.propertyloader.impl.PropertyLoaderOpener;

public abstract class OpenerConfig {
    public static List<PropertyLoaderOpener> defaultConfig() {
        List<PropertyLoaderOpener> result = new ArrayList<PropertyLoaderOpener>();

        result.add(contextClassLoader());
        result.add(filesystem());
        result.add(filesystemIn(System.getProperty("user.home")));

        return result;

    }

    public static PropertyLoaderOpener contextClassLoader() {
        return new ContextClassLoaderOpener();
    }

    public static PropertyLoaderOpener filesystem() {
        return new FilesystemOpener();
    }

    public static PropertyLoaderOpener filesystemIn(String directory) {
        return new FilesystemOpener(directory);
    }

    public static PropertyLoaderOpener classLoader(ClassLoader loader) {
        return new ClassLoaderOpener(loader);
    }

    public static PropertyLoaderOpener relativeTo(Class<?> reference) {
        return new RelativeToClass(reference);
    }
}
