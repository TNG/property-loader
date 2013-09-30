package com.tngtech.configbuilder.annotationprocessors;

import com.tngtech.configbuilder.BuilderConfiguration;
import com.tngtech.configbuilder.annotationprocessors.interfaces.ValueExtractorProcessor;
import com.tngtech.configbuilder.annotations.PropertyValue;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertyValueProcessor implements ValueExtractorProcessor {

    public String getValue(Annotation annotation, BuilderConfiguration builderConfiguration) {
        return builderConfiguration.getProperties().getProperty(((PropertyValue)annotation).value());
    }
}
