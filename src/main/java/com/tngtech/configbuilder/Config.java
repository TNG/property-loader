package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotations.DefaultValue;
import com.tngtech.configbuilder.annotations.PropertiesFile;

@PropertiesFile("hallo")
public class Config {

    @DefaultValue("user")
    private String value;

    public String getValue(){
        return value;
    }
}
