package com.tngtech.configbuilder.annotationprocessors;


import com.tngtech.configbuilder.annotations.PropertyLocations;
import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.annotations.PropertySuffixes;
import com.tngtech.configbuilder.annotations.PropertyValue;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertyLocationsProcessor implements AnnotationProcessor<PropertyLocations, ConfigBuilderContext, ConfigBuilderContext> {

    public ConfigBuilderContext process(PropertyLocations annotation, ConfigBuilderContext context) {
        context.getPropertyLoader().getLocations().clear();
        String[] locations = annotation.directories();
        for(String location : locations){
            context.getPropertyLoader().getLocations().atDirectory(location);
        }
        Class[] classes = annotation.resourcesForClasses();
        for(Class clazz : classes){
            context.getPropertyLoader().getLocations().atRelativeToClass(clazz);
        }
        return context;
    }

    public void configurePropertyLoader(Annotation annotation, ConfigBuilderContext context){
        PropertyLocations propertyLocations = (PropertyLocations)annotation;
        context.getPropertyLoader().getLocations().clear();
        String[] locations = propertyLocations.directories();
        for(String location : locations){
            context.getPropertyLoader().getLocations().atDirectory(location);
        }
        Class[] classes = propertyLocations.resourcesForClasses();
        for(Class clazz : classes){
            context.getPropertyLoader().getLocations().atRelativeToClass(clazz);
        }
    }
}
