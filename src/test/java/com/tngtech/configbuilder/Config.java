package com.tngtech.configbuilder;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.annotations.*;
import com.tngtech.configbuilder.impl.CollectionProvider;

import java.util.Collection;
import java.util.List;

@PropertiesFile("demoapp-configuration")
@ErrorMessageFile("errors")
public class Config {

    public class PidFixFactory implements CollectionProvider<String> {

        public Collection<String> getValues(String optionValue) {
            Collection<String> coll = Lists.newArrayList();
            coll.add(optionValue + " success");
            return coll;
        }
    }

    @DefaultValue("user")
    private String userName;

    @PropertyValue("a")
    private String helloWorld;

    @CommandLineValue("u")
    private String surName;

    @CommandLineValue("p")
    @ValueProvider(PidFixFactory.class)
    private Collection<String> pidFixes;

    public String getValue(){
        return userName;
    }

    public String getHelloWorld(){
        return helloWorld;
    }

    public String getSurName(){
        return surName;
    }

    public Collection<String> getPidFixes() {
        return pidFixes;
    }
}
