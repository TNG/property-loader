package com.tngtech.configbuilder.annotationhandlers;


import com.tngtech.configbuilder.annotations.DefaultValue;
import com.tngtech.configbuilder.impl.AnnotationHandler;

import java.lang.annotation.Annotation;

public class DefaultValueHandler extends AnnotationHandler{
    public String getString(Annotation annotation){
        DefaultValue defaultValue = (DefaultValue)annotation;
        return defaultValue.value();
    }
}
