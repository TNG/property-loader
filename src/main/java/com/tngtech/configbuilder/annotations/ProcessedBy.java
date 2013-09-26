package com.tngtech.configbuilder.annotations;


import com.tngtech.configbuilder.interfaces.AnnotationProcessor;

public @interface ProcessedBy {
    Class<? extends AnnotationProcessor> value();
}
