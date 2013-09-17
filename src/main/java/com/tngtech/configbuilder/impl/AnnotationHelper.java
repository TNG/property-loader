package com.tngtech.configbuilder.impl;


import com.tngtech.configbuilder.annotations.*;
import org.apache.commons.cli.CommandLine;

import java.lang.annotation.Annotation;
import java.util.Properties;

public class AnnotationHelper {

    private ConfigLoader configLoader;

    public AnnotationHelper(ConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    public ConfigLoader getConfigLoader(){
        return configLoader;
    }

    public Properties loadPropertiesFromAnnotation(Annotation annotation){

        if(annotation.annotationType() == PropertiesFile.class){
            PropertiesFile propertiesFile = (PropertiesFile) annotation;
            return configLoader.loadPropertiesFromAnnotation(propertiesFile);
        }
        else if(annotation.annotationType() == ErrorMessageFile.class){
            ErrorMessageFile errorMessageFile = (ErrorMessageFile) annotation;
            return configLoader.loadPropertiesFromAnnotation(errorMessageFile);
        }
        return new Properties();
    }

    public String loadStringFromAnnotation(Annotation annotation, CommandLine commandLineArgs, Properties properties){

        if(annotation.annotationType() == CommandLineValue.class){
            CommandLineValue commandLineValue = (CommandLineValue) annotation;
            return configLoader.loadStringFromAnnotation(commandLineValue, commandLineArgs);
        }
        else if(annotation.annotationType() == PropertyValue.class){
            PropertyValue propertyValue = (PropertyValue) annotation;
            return configLoader.loadStringFromAnnotation(propertyValue, properties);
        }
        else if(annotation.annotationType() == DefaultValue.class){
            DefaultValue defaultValue = (DefaultValue) annotation;
            return configLoader.loadStringFromAnnotation(defaultValue);
        }
        return null;
    }
}
