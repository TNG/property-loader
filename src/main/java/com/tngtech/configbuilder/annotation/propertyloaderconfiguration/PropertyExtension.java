package com.tngtech.configbuilder.annotation.propertyloaderconfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to specify a file extension for the properties files.<br>
 * <b>Usage:</b> <code>@PropertyExtension(value = "propertiesFileExtension")</code>
 */
@PropertyLoaderConfigurationAnnotation(PropertyExtensionProcessor.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyExtension {
    public String value();
}
