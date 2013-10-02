package com.tngtech.configbuilder.impl;

import com.tngtech.propertyloader.PropertyLoader;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class ErrorMessageSetup {

    private Properties errorMessages;

    public void initialize(String baseName, PropertyLoader propertyLoader){
        this.errorMessages = propertyLoader.load(baseName);
        if(this.errorMessages.isEmpty()) this.errorMessages = propertyLoader.withExtension("properties").load("errors_" + System.getProperty("user.language"));
        if(this.errorMessages.isEmpty()) this.errorMessages = propertyLoader.withExtension("properties").load("errors_en");
    }

    public String getString(String error) {
        return errorMessages.getProperty(error);
    }
}
