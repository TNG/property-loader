package com.tngtech.configbuilder.annotationprocessors;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.annotations.ErrorMessageFile;
import com.tngtech.configbuilder.annotations.LoadingOrder;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;

import java.lang.annotation.Annotation;


public class LoadingOrderProcessor implements AnnotationProcessor<LoadingOrder,ConfigBuilderContext,ConfigBuilderContext> {

    public ConfigBuilderContext process(LoadingOrder annotation, ConfigBuilderContext context) {
        context.setAnnotationOrder(annotation.value());
        return context;
    }
}
