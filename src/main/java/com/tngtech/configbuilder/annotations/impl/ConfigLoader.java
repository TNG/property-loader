package com.tngtech.configbuilder.annotations.impl;


import com.tngtech.configbuilder.annotations.ErrorMessageFile;
import com.tngtech.configbuilder.annotations.PropertiesFile;
import com.tngtech.propertyloader.PropertyLoader;

import java.lang.annotation.Annotation;
import java.util.Properties;

public class ConfigLoader {

    private PropertyLoader propertyLoader;

    public ConfigLoader(PropertyLoader propertyLoader) {
        this.propertyLoader = propertyLoader;
    }

    public Properties loadPropertiesFromAnnotation(Annotation annotation){

        String fileName = "", fileExtension = "";

        if(annotation.annotationType() == PropertiesFile.class){
            PropertiesFile propertiesFile = (PropertiesFile) annotation;
            fileName = propertiesFile.value();
            fileExtension = propertiesFile.fileExtension();
        }
        else if(annotation.annotationType() == ErrorMessageFile.class){
            ErrorMessageFile propertiesFile = (ErrorMessageFile) annotation;
            fileName = propertiesFile.value();
            fileExtension = propertiesFile.fileExtension();
        }
        Properties properties = propertyLoader.loadProperties(fileName,fileExtension);
        return properties;
    }
}
