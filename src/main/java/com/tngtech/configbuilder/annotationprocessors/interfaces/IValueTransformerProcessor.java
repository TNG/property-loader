package com.tngtech.configbuilder.annotationprocessors.interfaces;


import java.lang.annotation.Annotation;

public interface IValueTransformerProcessor<T> {
    public T transformString(Annotation annotation, String argument);
}
