package com.tngtech.configbuilder.annotation.valueextractor;


import com.tngtech.configbuilder.configuration.BuilderConfiguration;

import java.lang.annotation.Annotation;

public interface IValueExtractorProcessor {
    public String getValue(Annotation annotation, BuilderConfiguration argument);
}
