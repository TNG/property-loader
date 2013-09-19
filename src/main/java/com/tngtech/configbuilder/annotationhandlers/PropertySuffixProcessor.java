package com.tngtech.configbuilder.annotationhandlers;


import com.tngtech.configbuilder.annotations.PropertySuffixes;
import com.tngtech.configbuilder.impl.ConfigBuilderContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertySuffixProcessor implements AnnotationPropertyLoaderConfiguration{
    public void configurePropertyLoader(Annotation annotation, ConfigBuilderContext context){
        PropertySuffixes propertySuffixes = (PropertySuffixes)annotation;
        String[] suffixes = propertySuffixes.suffixes();
        for(String suffix : suffixes){
            context.getPropertyLoader().getSuffixes().addString(suffix);
        }
        if(propertySuffixes.hostnames()){
            context.getPropertyLoader().getSuffixes().addHostNames();
        }
    }
}
