package com.tngtech.configbuilder.annotation.propertyloaderconfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to specify properties files that the ConfigBuilder loads using the PropertyLoader.<br>
 * <b>Usage:</b> <code>@PropertiesFiles(value = {"file1","file2"})</code>
 */
@PropertyLoaderConfigurationAnnotation(PropertiesFilesProcessor.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertiesFiles {
    String[] value();
}
