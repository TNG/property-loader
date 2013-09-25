package com.tngtech.propertyloader.impl;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderOpener;
import com.tngtech.propertyloader.impl.interfaces.PropertyLocationsContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

@Component
@Scope("prototype")
public class DefaultPropertyLocationContainer implements PropertyLocationsContainer<DefaultPropertyLocationContainer> {

    private final PropertyLoaderFactory propertyLoaderFactory;

    @Autowired
    public DefaultPropertyLocationContainer(PropertyLoaderFactory propertyLoaderFactory){

        this.propertyLoaderFactory = propertyLoaderFactory;
    }

    private List<PropertyLoaderOpener> openers = Lists.newArrayList();

    public List<PropertyLoaderOpener> getOpeners()
    {
        return openers;
    }

    public DefaultPropertyLocationContainer atDefaultLocations(){

        atCurrentDirectory();
        atContextClassPath();
        atHomeDirectory();
        return this;
    }

    public DefaultPropertyLocationContainer atCurrentDirectory(){
        openers.add(propertyLoaderFactory.getURLFileOpener());
        return this;
    }

    public DefaultPropertyLocationContainer atHomeDirectory(){
        openers.add(propertyLoaderFactory.getURLFileOpener(System.getProperty("user.home")));
        return this;
    }

    public DefaultPropertyLocationContainer atDirectory(String directory){
        openers.add(propertyLoaderFactory.getURLFileOpener(directory));
        return this;
    }


    public DefaultPropertyLocationContainer atContextClassPath(){
        openers.add(propertyLoaderFactory.getContextClassLoaderOpener());
        return this;
    }

    public DefaultPropertyLocationContainer atRelativeToClass(Class<?> reference){
        openers.add(propertyLoaderFactory.getRelativeToClass(reference));
        return this;
    }

    public DefaultPropertyLocationContainer fromClassLoader(ClassLoader classLoader){
        openers.add(propertyLoaderFactory.getClassLoaderOpener(classLoader));
        return this;
    }

    public DefaultPropertyLocationContainer atBaseURL(URL url){
        openers.add(propertyLoaderFactory.getURLFileOpener(url));
        return this;
    }

    public DefaultPropertyLocationContainer clear() {
        openers.clear();
        return this;
    }

}
