package com.tngtech.configbuilder.annotationprocessors;

import com.tngtech.configbuilder.annotations.PropertyValue;
import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertyValueProcessor implements AnnotationProcessor<PropertyValue, ConfigBuilderContext, String> {

    public String getValue(Annotation annotation, ConfigBuilderContext context) {
        PropertyValue propertyValue = (PropertyValue) annotation;
        return context.getProperties().getProperty(propertyValue.value());
    }

    public String process(PropertyValue annotation, ConfigBuilderContext context) {
        return context.getProperties().getProperty(annotation.value());
    }
}
