package com.tngtech.configbuilder.annotationhandlers;


import com.tngtech.configbuilder.annotations.PropertyLocations;
import com.tngtech.configbuilder.impl.ConfigBuilderContext;

import java.lang.annotation.Annotation;

public class PropertyLocationsProcessor implements AnnotationPropertyLoaderConfiguration {
    public void configurePropertyLoader(Annotation annotation, ConfigBuilderContext context){
        PropertyLocations propertyLocations = (PropertyLocations)annotation;
        String[] locations = propertyLocations.value();
        for(String location : locations){
            context.getPropertyLoader().getLocations().atDirectory(location);
        }
    }
}
