package com.tngtech.propertyloader.impl;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

@Component
@Scope("prototype")
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
        openers.add(propertyLoaderFactory.getURLFileOpener());
        return this;
    }

    public PropertyLocation atHomeDirectory(){
        openers.add(propertyLoaderFactory.getURLFileOpener(System.getProperty("user.home")));
        return this;
    }

    public PropertyLocation atDirectory(String directory){
        openers.add(propertyLoaderFactory.getURLFileOpener(directory));
        return this;
    }

    public void clear() {
        openers.clear();
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
        openers.add(propertyLoaderFactory.getURLFileOpener(url));
        return this;
    }


}
