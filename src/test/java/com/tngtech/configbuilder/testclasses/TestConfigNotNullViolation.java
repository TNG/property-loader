package com.tngtech.configbuilder.testclasses;

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

import javax.validation.constraints.NotNull;

@PropertyExtension("testproperties")
@PropertySuffixes(extraSuffixes = {"test"})
@PropertyLocations(resourcesForClasses = {PropertyLoader.class},fromClassLoader = true)
@PropertiesFiles("demoapp-configuration")
@LoadingOrder(value = {CommandLineValue.class, PropertyValue.class, DefaultValue.class})
public class TestConfigNotNullViolation {

    public static class IntegerFactory implements FieldValueProvider<Integer> {

        public Integer getValue(String optionValue) {
            return null;
        }
    }

    @NotNull
    @DefaultValue("what")
    @ValueTransformer(IntegerFactory.class)
    private String string;
}
