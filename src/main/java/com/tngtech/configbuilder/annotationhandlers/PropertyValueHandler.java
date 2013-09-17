package com.tngtech.configbuilder.annotationhandlers;

import com.tngtech.configbuilder.annotations.PropertyValue;
import com.tngtech.configbuilder.impl.AnnotationHandler;

import java.lang.annotation.Annotation;

public class PropertyValueHandler extends AnnotationHandler {
    public String getString(Annotation annotation){
        PropertyValue propertyValue = (PropertyValue)annotation;
        return properties.getProperty(propertyValue.value());
    }
}
