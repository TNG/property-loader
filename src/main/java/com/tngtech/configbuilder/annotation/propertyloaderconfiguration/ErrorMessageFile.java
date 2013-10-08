package com.tngtech.configbuilder.annotation.propertyloaderconfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to specify a custom error message file for the ConfigBuilder.
 * The error message file is loaded by the PropertyLoader with the same settings as other properties files.<br>
 * <b>Usage:</b> <code>@ErrorMessageFile(value = "fileName")</code>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorMessageFile {
    String value();
}
