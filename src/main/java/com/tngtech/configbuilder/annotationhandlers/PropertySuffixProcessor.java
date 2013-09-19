package com.tngtech.configbuilder.annotationhandlers;


import com.tngtech.configbuilder.annotations.PropertySuffixes;
import com.tngtech.configbuilder.impl.ConfigBuilderContext;

import java.lang.annotation.Annotation;

public class PropertySuffixProcessor implements AnnotationPropertyLoaderConfiguration{
    public void configurePropertyLoader(Annotation annotation, ConfigBuilderContext context){
        PropertySuffixes propertyLocations = (PropertySuffixes)annotation;
        String[] suffixes = propertyLocations.value();
        for(String suffix : suffixes){
            context.getPropertyLoader().getSuffixes().addString(suffix);
        }
    }
}
