package com.tngtech.configbuilder;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.annotations.*;
import com.tngtech.configbuilder.interfaces.FieldValueProvider;
import com.tngtech.propertyloader.PropertyLoader;

import java.util.Collection;

@PropertyExtension("testproperties")
@PropertySuffixes(extraSuffixes = {"test"})
@PropertyLocations(resourcesForClasses = {PropertyLoader.class},fromClassLoader = true)
@PropertiesFiles("demoapp-configuration")
@LoadingOrder(value = {CommandLineValue.class, PropertyValue.class, DefaultValue.class})
public class TestConfig2 {

    public TestConfig2(String object, Integer i){

    }

    public static class PidFixFactory implements FieldValueProvider<Collection<String>> {

        public Collection<String> getValue(String optionValue) {
            Collection<String> coll = Lists.newArrayList();
            coll.add(optionValue + " success");
            return coll;
        }
    }

    @DefaultValue("user")
    private String userName;

    @PropertyValue("a")
    private String helloWorld;

    @CommandLineValue(shortOpt = "u", longOpt = "user")
    private String surName;

    @LoadingOrder(value = {CommandLineValue.class})
    @CommandLineValue(shortOpt = "p", longOpt = "pidFixFactory")
    @ValueTransformer(PidFixFactory.class)
    private Collection<String> pidFixes;

    public String getUserName(){
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
