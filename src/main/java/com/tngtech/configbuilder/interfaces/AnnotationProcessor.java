package com.tngtech.configbuilder.interfaces;


import java.lang.annotation.Annotation;

public interface AnnotationProcessor<A extends Annotation, T, E> {
    public E process(A annotation, T argument);
}
