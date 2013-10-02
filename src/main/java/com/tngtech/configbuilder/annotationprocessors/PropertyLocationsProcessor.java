package com.tngtech.configbuilder.annotationprocessors;


import com.tngtech.configbuilder.annotationprocessors.interfaces.IPropertyLoaderConfigurationProcessor;
import com.tngtech.configbuilder.annotations.PropertyLocations;
import com.tngtech.propertyloader.PropertyLoader;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertyLocationsProcessor implements IPropertyLoaderConfigurationProcessor {

    public void configurePropertyLoader(Annotation annotation, PropertyLoader propertyLoader) {
        propertyLoader.getLocations().clear();
        String[] locations = ((PropertyLocations)annotation).directories();
        for(String location : locations){
            propertyLoader.atDirectory(location);
        }
        Class[] classes = ((PropertyLocations)annotation).resourcesForClasses();
        for(Class clazz : classes){
            propertyLoader.atRelativeToClass(clazz);
        }
        if(((PropertyLocations) annotation).fromClassLoader()){
            propertyLoader.atContextClassPath();
        }
    }
}
