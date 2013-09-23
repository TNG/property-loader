package com.tngtech.configbuilder.annotationprocessors.implementations;


import com.tngtech.configbuilder.annotationprocessors.interfaces.AnnotationPropertyLoaderConfiguration;
import com.tngtech.configbuilder.annotations.PropertySuffixes;
import com.tngtech.configbuilder.ConfigBuilderContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertySuffixProcessor implements AnnotationPropertyLoaderConfiguration {
    public void configurePropertyLoader(Annotation annotation, ConfigBuilderContext context){
        PropertySuffixes propertySuffixes = (PropertySuffixes)annotation;
        context.getPropertyLoader().getSuffixes().clear();
        String[] suffixes = propertySuffixes.extraSuffixes();
        for(String suffix : suffixes){
            context.getPropertyLoader().getSuffixes().addString(suffix);
        }
        if(propertySuffixes.hostNames()){
            context.getPropertyLoader().getSuffixes().addLocalHostNames();
        }
    }
}
