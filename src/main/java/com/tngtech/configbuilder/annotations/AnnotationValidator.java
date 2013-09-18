package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.validators.annotation.AnnotationValidatorAbstract;

import java.lang.annotation.Annotation;

public @interface AnnotationValidator {
    Class<? extends AnnotationValidatorAbstract> value();
}
