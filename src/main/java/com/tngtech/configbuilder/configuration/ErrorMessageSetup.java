package com.tngtech.configbuilder.configuration;

import com.tngtech.propertyloader.PropertyLoader;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ErrorMessageSetup {

    private Properties errorMessages;

    public void initialize(String baseName, PropertyLoader propertyLoader){
        Locale locale = new Locale(System.getProperty("user.language"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("errors", locale);
        errorMessages = convertResourceBundleToProperties(resourceBundle);
        errorMessages.putAll(propertyLoader.load(baseName));
    }

    private Properties convertResourceBundleToProperties(ResourceBundle resource) {
        Properties properties = new Properties();

        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            properties.put(key, resource.getString(key));
        }
        return properties;
    }

    public String getErrorMessage(String error) {
        return errorMessages.getProperty(error);
    }

    public String getErrorMessage(Throwable e) {
        return errorMessages.getProperty(e.getClass().getName());
    }

    public String getErrorMessage(Throwable e, String... variables) {
        String message = errorMessages.getProperty(e.getClass().getName());
        return  String.format(message, variables);
    }

    public String getErrorMessage(String error, String... variables) {
        String message = errorMessages.getProperty(error);
        return String.format(message, variables);
    }
}
