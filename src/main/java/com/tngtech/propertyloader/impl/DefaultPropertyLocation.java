package com.tngtech.propertyloader.impl;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderOpener;
import com.tngtech.propertyloader.impl.interfaces.PropertyLocations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

@Component
@Scope("prototype")
public class DefaultPropertyLocation implements PropertyLocations<DefaultPropertyLocation> {

    private final PropertyLoaderFactory propertyLoaderFactory;

    @Autowired
    public DefaultPropertyLocation(PropertyLoaderFactory propertyLoaderFactory){

        this.propertyLoaderFactory = propertyLoaderFactory;
    }

    private List<PropertyLoaderOpener> openers = Lists.newArrayList();

    public List<PropertyLoaderOpener> getOpeners()
    {
        return openers;
    }

    public DefaultPropertyLocation atDefaultLocations(){

        atCurrentDirectory();
        atContextClassPath();
        atHomeDirectory();
        return this;
    }

    public DefaultPropertyLocation atCurrentDirectory(){
        openers.add(propertyLoaderFactory.getURLFileOpener());
        return this;
    }

    public DefaultPropertyLocation atHomeDirectory(){
        openers.add(propertyLoaderFactory.getURLFileOpener(System.getProperty("user.home")));
        return this;
    }

    public DefaultPropertyLocation atDirectory(String directory){
        openers.add(propertyLoaderFactory.getURLFileOpener(directory));
        return this;
    }


    public DefaultPropertyLocation atContextClassPath(){
        openers.add(propertyLoaderFactory.getContextClassLoaderOpener());
        return this;
    }

    public DefaultPropertyLocation atRelativeToClass(Class<?> reference){
        openers.add(propertyLoaderFactory.getRelativeToClass(reference));
        return this;
    }

    public DefaultPropertyLocation fromClassLoader(ClassLoader classLoader){
        openers.add(propertyLoaderFactory.getClassLoaderOpener(classLoader));
        return this;
    }

    public DefaultPropertyLocation atBaseURL(URL url){
        openers.add(propertyLoaderFactory.getURLFileOpener(url));
        return this;
    }

    public DefaultPropertyLocation clear() {
        openers.clear();
        return this;
    }

}
