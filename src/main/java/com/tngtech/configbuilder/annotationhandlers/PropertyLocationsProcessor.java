package com.tngtech.configbuilder.annotationhandlers;


import com.tngtech.configbuilder.annotations.PropertyLocations;
import com.tngtech.configbuilder.impl.ConfigBuilderContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertyLocationsProcessor implements AnnotationPropertyLoaderConfiguration {
    public void configurePropertyLoader(Annotation annotation, ConfigBuilderContext context){
        PropertyLocations propertyLocations = (PropertyLocations)annotation;
        context.getPropertyLoader().getLocations().clear();
        String[] locations = propertyLocations.directories();
        for(String location : locations){
            context.getPropertyLoader().getLocations().atDirectory(location);
        }
        Class[] classes = propertyLocations.resourcesforclasses();
        for(Class clazz : classes){
            context.getPropertyLoader().getLocations().atRelativeToClass(clazz);
        }
    }
}
