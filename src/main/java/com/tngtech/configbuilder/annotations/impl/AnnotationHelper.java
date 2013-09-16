package com.tngtech.configbuilder.annotations.impl;


import com.tngtech.configbuilder.annotations.*;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Properties;

public class AnnotationHelper {

    private ConfigLoader configLoader;

    public AnnotationHelper(ConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    public Properties loadPropertiesFromAnnotations(Annotation[] annotations){

        for(Annotation annotation : annotations){
            if(annotation.annotationType() == PropertiesFile.class){
                PropertiesFile propertiesFile = (PropertiesFile) annotation;
                return configLoader.loadPropertiesFromAnnotation(propertiesFile);
            }
            else if(annotation.annotationType() == ErrorMessageFile.class){
                ErrorMessageFile errorMessageFile = (ErrorMessageFile) annotation;
                return configLoader.loadPropertiesFromAnnotation(errorMessageFile);
            }
        }
        return new Properties();
    }

    public String loadStringFromAnnotations(Annotation[] annotations, Map<String,String> commandLineArgs, Properties properties){


        for(Annotation annotation : annotations){
            if(annotation.annotationType() == DefaultValue.class){
                DefaultValue defaultValue = (DefaultValue) annotation;
                return configLoader.loadStringFromAnnotation(defaultValue);
            }
            else if(annotation.annotationType() == PropertyValue.class){
                PropertyValue propertyValue = (PropertyValue) annotation;
                return configLoader.loadStringFromAnnotation(propertyValue, properties);
            }
            else if(annotation.annotationType() == CommandLineValue.class){
                CommandLineValue commandLineValue = (CommandLineValue) annotation;
                return configLoader.loadStringFromAnnotation(commandLineValue, commandLineArgs);
            }
        }
        return null;
    }
}
