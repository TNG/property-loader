package com.tngtech.configbuilder.validators.annotation;

import java.lang.annotation.Annotation;

public abstract class AnnotationValidatorAbstract {
    public abstract void validate(Annotation annotation);
}
