package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.openers.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PropertyLocation {

    public PropertyLocation(){

    }

    private List<PropertyLoaderOpener> openers = new ArrayList<PropertyLoaderOpener>();

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
        openers.add(new FilesystemOpener());
        return this;
    }

    public PropertyLocation atHomeDirectory(){
        openers.add(new FilesystemOpener(System.getProperty("user.home")));
        return this;
    }

    public PropertyLocation atDirectory(String directory){
        openers.add(new FilesystemOpener(directory));
        return this;
    }

    public PropertyLocation atContextClassPath(){
        openers.add(new ContextClassLoaderOpener());
        return this;
    }

    public PropertyLocation atRelativeToClass(Class<?> reference){
        openers.add(new RelativeToClass(reference));
        return this;
    }

    public PropertyLocation fromClassLoader(ClassLoader classLoader){
        openers.add(new ClassLoaderOpener(classLoader));
        return this;
    }

    public PropertyLocation atBaseURL(URL url){
        openers.add(new WebOpener(url));
        return this;
    }

}
