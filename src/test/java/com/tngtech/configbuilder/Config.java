package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotations.*;

@PropertiesFile("demoapp-configuration")
@ErrorMessageFile("errors")
public class Config {

    @DefaultValue("user")
    private String userName;

    @PropertyValue("a")
    private String helloWorld;

    @CommandLineValue("u")
    private String surName;

    public String getValue(){
        return userName;
    }

    public String getHelloWorld(){
        return helloWorld;
    }

    public String getSurName(){
        return surName;
    }
}
