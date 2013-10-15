package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.filters.*;
import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderFilter;
import com.tngtech.propertyloader.impl.openers.ClassLoaderOpener;
import com.tngtech.propertyloader.impl.openers.ContextClassLoaderOpener;
import com.tngtech.propertyloader.impl.openers.RelativeToClassOpener;
import com.tngtech.propertyloader.impl.openers.URLFileOpener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;
import java.util.Stack;

public class PropertyLoaderFactory {

    public <T> T getBean(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

    public Properties getEmptyProperties() {
        return new Properties();
    }

    public InputStreamReader getInputStreamReader(InputStream stream, String encoding) throws IOException {
        return new InputStreamReader(stream, encoding);
    }

    public URLFileOpener getURLFileOpener() {
        return new URLFileOpener();
    }

    public URLFileOpener getURLFileOpener(String directory) {
        return new URLFileOpener(directory);
    }

    public ContextClassLoaderOpener getContextClassLoaderOpener() {
        return new ContextClassLoaderOpener();
    }

    public RelativeToClassOpener getRelativeToClass(Class<?> tClass) {
        return new RelativeToClassOpener(tClass);
    }

    public ClassLoaderOpener getClassLoaderOpener(ClassLoader classLoader) {
        return new ClassLoaderOpener(classLoader);
    }

    public URLFileOpener getURLFileOpener(URL url) {
        return new URLFileOpener(url);
    }

    public PropertyLoaderFilter getVariableResolvingFilter() {
        return new VariableResolvingFilter();
    }

    public PropertyLoaderFilter getEnvironmentResolvingFilter() {
        return new EnvironmentResolvingFilter();
    }

    public PropertyLoaderFilter getWarnIfPropertyHasToBeDefined() {
        return new ThrowIfPropertyHasToBeDefined();
    }

    public PropertyLoaderFilter getWarnOnSurroundingWhitespace() {
        return new WarnOnSurroundingWhitespace();
    }

    public Stack<String> getEmptyFileNameStack() {
        return new Stack<>();
    }

    public StringBuilder getStringBuilder() {
        return new StringBuilder();
    }

    public PropertyLoaderFilter getDecryptingFilter() {
        return new DecryptingFilter();
    }
}
