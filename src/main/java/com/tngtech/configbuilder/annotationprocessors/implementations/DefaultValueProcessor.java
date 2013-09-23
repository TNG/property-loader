package com.tngtech.configbuilder.annotationprocessors.implementations;


import com.tngtech.configbuilder.annotationprocessors.interfaces.AnnotationValueExtractor;
import com.tngtech.configbuilder.annotations.DefaultValue;
import com.tngtech.configbuilder.ConfigBuilderContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class DefaultValueProcessor implements AnnotationValueExtractor {
    @Override
    public String getValue(Annotation annotation, ConfigBuilderContext context) {
        DefaultValue defaultValue = (DefaultValue) annotation;
        return defaultValue.value();
    }
}
