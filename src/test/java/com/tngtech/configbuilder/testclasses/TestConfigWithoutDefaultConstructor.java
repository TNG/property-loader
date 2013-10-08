package com.tngtech.configbuilder.testclasses;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.FieldValueProvider;
import com.tngtech.configbuilder.annotation.configuration.LoadingOrder;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertiesFiles;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyExtension;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyLocations;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertySuffixes;
import com.tngtech.configbuilder.annotation.valueextractor.CommandLineValue;
import com.tngtech.configbuilder.annotation.valueextractor.DefaultValue;
import com.tngtech.configbuilder.annotation.valueextractor.PropertyValue;
import com.tngtech.configbuilder.annotation.valuetransformer.ValueTransformer;
import com.tngtech.propertyloader.PropertyLoader;

import java.util.Collection;

@PropertyExtension("testproperties")
@PropertySuffixes(extraSuffixes = {"test"})
@PropertyLocations(resourcesForClasses = {PropertyLoader.class})
@PropertiesFiles("demoapp-configuration")
@LoadingOrder(value = {CommandLineValue.class, PropertyValue.class, DefaultValue.class})
public class TestConfigWithoutDefaultConstructor {

    private int number;

    public TestConfigWithoutDefaultConstructor(Integer i){
        this.number = i;
    }


    public int getNumber(){
        return number;
    }
}
