package com.tngtech.configbuilder.annotationprocessors;

import com.tngtech.configbuilder.impl.BuilderConfiguration;
import com.tngtech.configbuilder.annotationprocessors.interfaces.ValueExtractorProcessor;
import com.tngtech.configbuilder.annotations.DefaultValue;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class DefaultValueProcessor  implements ValueExtractorProcessor {

    public String getValue(Annotation annotation, BuilderConfiguration builderConfiguration) {
        return ((DefaultValue)annotation).value();
    }
}
