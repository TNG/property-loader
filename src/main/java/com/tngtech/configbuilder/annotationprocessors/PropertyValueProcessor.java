package com.tngtech.configbuilder.annotationprocessors;

import com.tngtech.configbuilder.ResultConfiguration;
import com.tngtech.configbuilder.annotationprocessors.interfaces.ValueExtractorProcessor;
import com.tngtech.configbuilder.annotations.PropertyValue;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertyValueProcessor implements ValueExtractorProcessor {

    public String getValue(Annotation annotation, ResultConfiguration resultConfiguration) {
        return resultConfiguration.getProperties().getProperty(((PropertyValue)annotation).value());
    }
}
