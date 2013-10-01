package com.tngtech.configbuilder.impl;

import com.tngtech.configbuilder.annotationprocessors.interfaces.IValueExtractorProcessor;
import com.tngtech.configbuilder.annotationprocessors.interfaces.IValueTransformerProcessor;
import com.tngtech.configbuilder.annotations.LoadingOrder;
import com.tngtech.configbuilder.annotations.metaannotations.ValueExtractorAnnotation;
import com.tngtech.configbuilder.annotations.metaannotations.ValueTransformerAnnotation;
import com.tngtech.configbuilder.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class FieldValueExtractor {
    private final AnnotationUtils annotationUtils;

    @Autowired
    public FieldValueExtractor(AnnotationUtils annotationUtils) {
        this.annotationUtils = annotationUtils;
    }


    public Object extractValue(Field field, BuilderConfiguration builderConfiguration) {

        String value = null;
        Class[] annotationOrderOfField = field.isAnnotationPresent(LoadingOrder.class) ? field.getAnnotation(LoadingOrder.class).value() : builderConfiguration.getAnnotationOrder();
        Class<? extends IValueExtractorProcessor> processor;

        for (Annotation annotation : annotationUtils.getAnnotationsInOrder(field, annotationOrderOfField)) {
            processor = annotation.annotationType().getAnnotation(ValueExtractorAnnotation.class).value();
            value = Context.getBean(processor).getValue(annotation, builderConfiguration);
            if (value != null) break;
        }

        return getTransformedFieldValueIfApplicable(field, value);
    }

    private Object getTransformedFieldValueIfApplicable(Field field, String value) {
        Object fieldValue = value;
        Class<? extends IValueTransformerProcessor<Object>> processor;

        for(Annotation annotation : annotationUtils.getAnnotationsOfType(field, ValueTransformerAnnotation.class)){
            processor = annotation.annotationType().getAnnotation(ValueTransformerAnnotation.class).value();
            fieldValue = Context.getBean(processor).transformString(annotation, value);
        }

        return fieldValue;
    }
}