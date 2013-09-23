package com.tngtech.configbuilder.annotations.config;

import com.tngtech.configbuilder.annotationprocessors.interfaces.AnnotationValidatorAbstract;

public @interface AnnotationValidator {
    Class<? extends AnnotationValidatorAbstract> value();
}
