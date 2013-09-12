package com.tngtech.propertyloader.impl;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.impl.openers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class PropertyLocation {

    private final PropertyLoaderFactory propertyLoaderFactory;

    @Autowired
    public PropertyLocation(PropertyLoaderFactory propertyLoaderFactory){

        this.propertyLoaderFactory = propertyLoaderFactory;
    }

    private List<PropertyLoaderOpener> openers = Lists.newArrayList();

    public List<PropertyLoaderOpener> getOpeners()
    {
        return openers;
    }

    public PropertyLocation atDefaultLocations(){
        atCurrentDirectory();
        atContextClassPath();
        atHomeDirectory();
        return this;
    }

    public PropertyLocation atCurrentDirectory(){
        openers.add(propertyLoaderFactory.getFilesystemOpener());
        return this;
    }

    public PropertyLocation atHomeDirectory(){
        openers.add(propertyLoaderFactory.getFilesystemOpener(System.getProperty("user.home")));
        return this;
    }

    public PropertyLocation atDirectory(String directory){
        openers.add(propertyLoaderFactory.getFilesystemOpener(directory));
        return this;
    }

    public PropertyLocation atContextClassPath(){
        openers.add(propertyLoaderFactory.getContextClassLoaderOpener());
        return this;
    }

    public PropertyLocation atRelativeToClass(Class<?> reference){
        openers.add(propertyLoaderFactory.getRelativeToClass(reference));
        return this;
    }

    public PropertyLocation fromClassLoader(ClassLoader classLoader){
        openers.add(propertyLoaderFactory.getClassLoaderOpener(classLoader));
        return this;
    }

    public PropertyLocation atBaseURL(URL url){
        openers.add(propertyLoaderFactory.getWebOpener(url));
        return this;
    }

}
