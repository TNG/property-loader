package com.tngtech.configbuilder.annotationprocessors.interfaces;

import java.lang.annotation.Annotation;

public interface AnnotationValidatorAbstract {
    public void validate(Annotation annotation);
}
