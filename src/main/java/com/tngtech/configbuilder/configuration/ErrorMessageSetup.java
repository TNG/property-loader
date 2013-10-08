package com.tngtech.configbuilder.configuration;

import com.tngtech.propertyloader.PropertyLoader;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Stores error messages for the ConfigBuilder in a Properties object.
 */
@Component
public class ErrorMessageSetup {

    private Properties errorMessages;

    /**
     * loads the default error messages for the system locale, then merges them with additional error messages loaded with the PropertyLoader
     * @param baseName the filename for additional error messages
     * @param propertyLoader the PropertyLoader used to load additional error messages
     */
    public void initialize(String baseName, PropertyLoader propertyLoader){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("errors");
        errorMessages = convertResourceBundleToProperties(resourceBundle);
        if(baseName != null){
            errorMessages.putAll(propertyLoader.load(baseName));
        }
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

    public String getErrorMessage(Throwable e, String... variables) {
        String message = errorMessages.getProperty(e.getClass().getName());
        return message == null ? String.format(errorMessages.getProperty("standardMessage"),e.getClass().getName()) : String.format(message, variables);
    }

    public String getErrorMessage(Class exceptionClass, String... variables) {
        String message = errorMessages.getProperty(exceptionClass.getName());
        return  message == null ? String.format(errorMessages.getProperty("standardMessage"), exceptionClass.getName()) : String.format(message, variables);
    }
}
