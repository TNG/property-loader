package com.tngtech.configbuilder.annotation.valueextractor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to specify a default value for the annotated field.<br>
 * <b>Usage:</b> <code>@DefaultValue(value = "value")</code>
 */
@ValueExtractorAnnotation(DefaultValueProcessor.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultValue {
    String value();
}
