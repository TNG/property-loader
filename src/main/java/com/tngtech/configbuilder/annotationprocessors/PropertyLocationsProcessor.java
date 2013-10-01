package com.tngtech.configbuilder.annotationprocessors;


import com.tngtech.configbuilder.annotationprocessors.interfaces.IBuilderConfigurationProcessor;
import com.tngtech.configbuilder.annotations.PropertyLocations;
import com.tngtech.propertyloader.PropertyLoader;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertyLocationsProcessor implements IBuilderConfigurationProcessor {

    public void configurePropertyLoader(Annotation annotation, PropertyLoader propertyLoader) {
        propertyLoader.getLocations().clear();
        String[] locations = ((PropertyLocations)annotation).directories();
        for(String location : locations){
            propertyLoader.getLocations().atDirectory(location);
        }
        Class[] classes = ((PropertyLocations)annotation).resourcesForClasses();
        for(Class clazz : classes){
            propertyLoader.getLocations().atRelativeToClass(clazz);
        }
    }
}
