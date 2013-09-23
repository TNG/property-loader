package com.tngtech.configbuilder.annotationprocessors.implementations;

import com.tngtech.configbuilder.annotationprocessors.interfaces.AnnotationValueExtractor;
import com.tngtech.configbuilder.annotations.PropertyValue;
import com.tngtech.configbuilder.ConfigBuilderContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertyValueProcessor implements AnnotationValueExtractor {
    public String getValue(Annotation annotation, ConfigBuilderContext context) {
        PropertyValue propertyValue = (PropertyValue) annotation;
        return context.getProperties().getProperty(propertyValue.value());
    }
}
