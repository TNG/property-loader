package com.tngtech.configbuilder.annotationprocessors.interfaces;


import com.tngtech.configbuilder.BuilderConfiguration;

import java.lang.annotation.Annotation;

public interface BuilderConfigurationProcessor {
    public void updateBuilderConfiguration(Annotation annotation, BuilderConfiguration argument);
}
