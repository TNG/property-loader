package com.tngtech.configbuilder.annotationhandlers;

import com.tngtech.configbuilder.annotations.PropertyValue;
import com.tngtech.configbuilder.impl.ConfigBuilderContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertyValueHandler implements AnnotationValueExtractor {
    public String getValue(Annotation annotation, ConfigBuilderContext context) {
        PropertyValue propertyValue = (PropertyValue) annotation;
        return context.getProperties().getProperty(propertyValue.value());
    }
}
