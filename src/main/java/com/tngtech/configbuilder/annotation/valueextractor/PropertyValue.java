package com.tngtech.configbuilder.annotation.valueextractor;

import java.lang.annotation.*;

@ValueExtractorAnnotation(PropertyValueProcessor.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyValue{
    String value();
}
