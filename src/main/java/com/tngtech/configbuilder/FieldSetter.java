package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotationprocessors.interfaces.ValueExtractorProcessor;
import com.tngtech.configbuilder.annotationprocessors.interfaces.ValueTransformerProcessor;
import com.tngtech.configbuilder.annotations.LoadingOrder;
import com.tngtech.configbuilder.annotations.ValueTransformer;
import com.tngtech.configbuilder.annotations.metaannotations.ValueExtractorAnnotation;
import com.tngtech.configbuilder.annotations.metaannotations.ValueTransformerAnnotation;
import com.tngtech.configbuilder.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class FieldSetter<T> {
    private final AnnotationUtils annotationUtils;

    @Autowired
    public FieldSetter(AnnotationUtils annotationUtils) {
        this.annotationUtils = annotationUtils;
    }

    public void setFields(T instanceOfConfigClass, BuilderConfiguration builderConfiguration) throws IllegalAccessException {

        for (Field field : instanceOfConfigClass.getClass().getDeclaredFields()) {
            String value = extractValue(field, builderConfiguration);
            Object transformedValue = getTransformedFieldValueIfApplicable(field, value);
            verifyCorrectTargetType(field, transformedValue);
            field.setAccessible(true);
            field.set(instanceOfConfigClass, transformedValue);
        }
    }

    private String extractValue(Field field, BuilderConfiguration builderConfiguration) {

        String value = null;
        Class[] annotationOrderOfField = field.isAnnotationPresent(LoadingOrder.class) ? field.getAnnotation(LoadingOrder.class).value() : builderConfiguration.getAnnotationOrder();
        Class<? extends ValueExtractorProcessor> processor;

        for (Annotation annotation : annotationUtils.getAnnotationsInOrder(field, annotationOrderOfField)) {
            processor = annotation.annotationType().getAnnotation(ValueExtractorAnnotation.class).value();
            value = Context.getBean(processor).getValue(annotation, builderConfiguration);
            if (value != null) break;
        }

        return value;
    }

    private Object getTransformedFieldValueIfApplicable(Field field, String value) {
        Object fieldValue = value;
        Class<? extends ValueTransformerProcessor<Object>> processor;

        for(Annotation annotation : annotationUtils.getAnnotationsOfType(field, ValueTransformerAnnotation.class)){
            processor = annotation.annotationType().getAnnotation(ValueTransformerAnnotation.class).value();
            fieldValue = Context.getBean(processor).transformString(annotation, value);
        }

        return fieldValue;
    }

    private void verifyCorrectTargetType(Field field, Object fieldValue) {
        if (fieldValue != null && !field.getType().isAssignableFrom(fieldValue.getClass())) {
            throw new ConfigBuilderException(String.format("cannot set field '%s' of type %s to object of type %s", field.getName(), field.getType().getName(), fieldValue.getClass().getName()));
        }
    }
}