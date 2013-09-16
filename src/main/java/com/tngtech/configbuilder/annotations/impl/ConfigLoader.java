package com.tngtech.configbuilder.annotations.impl;


import com.tngtech.configbuilder.annotations.*;
import com.tngtech.propertyloader.PropertyLoader;

import java.util.Map;
import java.util.Properties;

public class ConfigLoader {

    private PropertyLoader propertyLoader;

    public ConfigLoader(PropertyLoader propertyLoader) {
        this.propertyLoader = propertyLoader;
    }

    public Properties loadPropertiesFromAnnotation(PropertiesFile propertiesFile){

        String fileName = propertiesFile.value();
        String fileExtension = propertiesFile.fileExtension();
        Properties properties = propertyLoader.loadProperties(fileName,fileExtension);
        return properties;
    }

    public Properties loadPropertiesFromAnnotation(ErrorMessageFile errorMessageFile){

        String fileName = errorMessageFile.value();
        String fileExtension = errorMessageFile.fileExtension();
        Properties properties = propertyLoader.loadProperties(fileName,fileExtension);
        return properties;
    }

    public String loadStringFromAnnotation(DefaultValue defaultValue){
        return defaultValue.value();
    }

    public String loadStringFromAnnotation(PropertyValue propertyValue, Properties properties){
        return properties.getProperty(propertyValue.value());
    }

    public String loadStringFromAnnotation(CommandLineValue commandLineValue, Map<String,String> commandLineArgs){
        return commandLineArgs.get(commandLineValue.value());
    }
}
