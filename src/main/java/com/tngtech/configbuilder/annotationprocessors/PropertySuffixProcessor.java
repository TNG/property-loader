package com.tngtech.configbuilder.annotationprocessors;


import com.tngtech.configbuilder.annotationprocessors.interfaces.IBuilderConfigurationProcessor;
import com.tngtech.configbuilder.annotations.PropertySuffixes;
import com.tngtech.propertyloader.PropertyLoader;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertySuffixProcessor implements IBuilderConfigurationProcessor {

    public void configurePropertyLoader(Annotation annotation, PropertyLoader propertyLoader) {
        propertyLoader.getSuffixes().clear();
        String[] suffixes = ((PropertySuffixes)annotation).extraSuffixes();
        for(String suffix : suffixes){
            propertyLoader.getSuffixes().addString(suffix);
        }
        if(((PropertySuffixes)annotation).hostNames()){
            propertyLoader.getSuffixes().addLocalHostNames();
        }
    }
}
