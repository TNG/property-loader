package com.tngtech.configbuilder.annotation.propertyloaderconfiguration;


import com.tngtech.propertyloader.PropertyLoader;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertySuffixProcessor implements IPropertyLoaderConfigurationProcessor {

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
