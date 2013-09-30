package com.tngtech.configbuilder.annotationprocessors.interfaces;


import com.tngtech.configbuilder.impl.BuilderConfiguration;

import java.lang.annotation.Annotation;

public interface ValueExtractorProcessor {
    public String getValue(Annotation annotation, BuilderConfiguration argument);
}
