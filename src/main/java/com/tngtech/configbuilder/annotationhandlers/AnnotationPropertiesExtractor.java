package com.tngtech.configbuilder.annotationhandlers;


import com.tngtech.configbuilder.annotations.PropertiesFile;
import com.tngtech.configbuilder.impl.ConfigBuilderContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Properties;

@Component
public class AnnotationPropertiesExtractor {

    Properties getProperties(Annotation annotation, ConfigBuilderContext context){
        PropertiesFile propertiesFile = (PropertiesFile)annotation;
        String fileName = propertiesFile.value();
        Properties properties = context.getPropertyLoader().loadProperties(fileName);
        return properties;
    }
}
