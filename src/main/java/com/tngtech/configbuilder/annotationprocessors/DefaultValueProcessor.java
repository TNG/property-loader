package com.tngtech.configbuilder.annotationprocessors;

import com.tngtech.configbuilder.annotations.DefaultValue;
import com.tngtech.configbuilder.ConfigBuilderContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class DefaultValueProcessor {

    public String getValue(Annotation annotation, ConfigBuilderContext context) {
        DefaultValue defaultValue = (DefaultValue) annotation;
        return defaultValue.value();
    }
}
