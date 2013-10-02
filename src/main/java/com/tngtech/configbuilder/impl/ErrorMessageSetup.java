package com.tngtech.configbuilder.impl;

import com.tngtech.configbuilder.ConfigBuilderException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Component
public class ErrorMessageSetup {

    private ResourceBundle resourceBundle;

    @PostConstruct
    public void initialize(){
        try{
            this.resourceBundle = ResourceBundle.getBundle("errors", Locale.getDefault());
        }
        catch (MissingResourceException e) {
            this.resourceBundle = ResourceBundle.getBundle("errors", new Locale("en","US"));
        }
    }

    public void initialize(String baseName) {

        try{
            this.resourceBundle = ResourceBundle.getBundle("errors", Locale.getDefault());
        }
        catch (MissingResourceException e) {
            throw new ConfigBuilderException(this.getString("errorMessageFileException").replace("${fileName}",baseName + "_" + Locale.getDefault().getLanguage()));
        }
    }

    public String getString(String error) {
        return resourceBundle.getString(error);
    }
}
