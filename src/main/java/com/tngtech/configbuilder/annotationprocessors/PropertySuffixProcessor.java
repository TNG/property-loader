package com.tngtech.configbuilder.annotationprocessors;


import com.tngtech.configbuilder.BuilderConfiguration;
import com.tngtech.configbuilder.annotations.PropertySuffixes;
import com.tngtech.configbuilder.annotationprocessors.interfaces.BuilderConfigurationProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertySuffixProcessor implements BuilderConfigurationProcessor {

    public void updateBuilderConfiguration(Annotation annotation, BuilderConfiguration context) {
        context.getPropertyLoader().getSuffixes().clear();
        String[] suffixes = ((PropertySuffixes)annotation).extraSuffixes();
        for(String suffix : suffixes){
            context.getPropertyLoader().getSuffixes().addString(suffix);
        }
        if(((PropertySuffixes)annotation).hostNames()){
            context.getPropertyLoader().getSuffixes().addLocalHostNames();
        }
    }
}
