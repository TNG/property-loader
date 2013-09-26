package com.tngtech.configbuilder.annotationprocessors;


import com.tngtech.configbuilder.annotations.PropertySuffixes;
import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.annotations.PropertyValue;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertySuffixProcessor implements AnnotationProcessor<PropertySuffixes, ConfigBuilderContext, ConfigBuilderContext> {

    public ConfigBuilderContext process(PropertySuffixes annotation, ConfigBuilderContext context) {
        context.getPropertyLoader().getSuffixes().clear();
        String[] suffixes = annotation.extraSuffixes();
        for(String suffix : suffixes){
            context.getPropertyLoader().getSuffixes().addString(suffix);
        }
        if(annotation.hostNames()){
            context.getPropertyLoader().getSuffixes().addLocalHostNames();
        }
        return context;
    }

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
