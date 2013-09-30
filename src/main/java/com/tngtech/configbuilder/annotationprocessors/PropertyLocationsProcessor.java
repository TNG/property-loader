package com.tngtech.configbuilder.annotationprocessors;


import com.tngtech.configbuilder.BuilderConfiguration;
import com.tngtech.configbuilder.annotations.PropertyLocations;
import com.tngtech.configbuilder.annotationprocessors.interfaces.BuilderConfigurationProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertyLocationsProcessor implements BuilderConfigurationProcessor {

    public void updateBuilderConfiguration(Annotation annotation, BuilderConfiguration context) {
        context.getPropertyLoader().getLocations().clear();
        String[] locations = ((PropertyLocations)annotation).directories();
        for(String location : locations){
            context.getPropertyLoader().getLocations().atDirectory(location);
        }
        Class[] classes = ((PropertyLocations)annotation).resourcesForClasses();
        for(Class clazz : classes){
            context.getPropertyLoader().getLocations().atRelativeToClass(clazz);
        }
    }
}
