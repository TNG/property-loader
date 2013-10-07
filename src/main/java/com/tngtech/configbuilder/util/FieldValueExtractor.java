package com.tngtech.configbuilder.util;

import com.tngtech.configbuilder.annotation.valueextractor.IValueExtractorProcessor;
import com.tngtech.configbuilder.annotation.valuetransformer.IValueTransformerProcessor;
import com.tngtech.configbuilder.annotation.configuration.LoadingOrder;
import com.tngtech.configbuilder.annotation.valueextractor.ValueExtractorAnnotation;
import com.tngtech.configbuilder.annotation.valuetransformer.ValueTransformerAnnotation;
import com.tngtech.configbuilder.configuration.BuilderConfiguration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class FieldValueExtractor {
    private final AnnotationHelper annotationHelper;
    private final BeanFactory beanFactory;

    @Autowired
    public FieldValueExtractor(AnnotationHelper annotationHelper, BeanFactory beanFactory) {
        this.annotationHelper = annotationHelper;
        this.beanFactory = beanFactory;
    }


    public Object extractValue(Field field, BuilderConfiguration builderConfiguration) {
        String value = extractString(field,builderConfiguration);
        return getTransformedFieldValueIfApplicable(field, value);
    }

    private String extractString(Field field, BuilderConfiguration builderConfiguration){
        String value = null;
        Class<? extends Annotation>[] annotationOrderOfField = field.isAnnotationPresent(LoadingOrder.class) ? field.getAnnotation(LoadingOrder.class).value() : builderConfiguration.getAnnotationOrder();
        Class<? extends IValueExtractorProcessor> processor;

        for (Annotation annotation : annotationHelper.getAnnotationsInOrder(field, annotationOrderOfField)) {
            processor = annotation.annotationType().getAnnotation(ValueExtractorAnnotation.class).value();
            value = beanFactory.getBean(processor).getValue(annotation, builderConfiguration);
            if (value != null) {
                break;
            }
        }
        return value;
    }

    private Object getTransformedFieldValueIfApplicable(Field field, String value) {
        Object fieldValue = value;
        Class<? extends IValueTransformerProcessor<Object>> processor;

        for(Annotation annotation : annotationHelper.getAnnotationsAnnotatedWith(field.getDeclaredAnnotations(), ValueTransformerAnnotation.class)){
            processor = annotation.annotationType().getAnnotation(ValueTransformerAnnotation.class).value();
            fieldValue = beanFactory.getBean(processor).transformString(annotation, value);
        }

        return fieldValue;
    }
}