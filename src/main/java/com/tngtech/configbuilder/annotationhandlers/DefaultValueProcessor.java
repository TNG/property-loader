package com.tngtech.configbuilder.annotationhandlers;


import com.tngtech.configbuilder.annotations.DefaultValue;
import com.tngtech.configbuilder.impl.ConfigBuilderContext;
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
