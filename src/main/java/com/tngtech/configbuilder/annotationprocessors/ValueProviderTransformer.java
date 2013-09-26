package com.tngtech.configbuilder.annotationprocessors;

import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.ConfigBuilderException;
import com.tngtech.configbuilder.annotations.PropertyValue;
import com.tngtech.configbuilder.annotations.ValueProvider;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class ValueProviderTransformer implements AnnotationProcessor<ValueProvider, String, Object> {

    public Object process(ValueProvider annotation, String fieldString) {
        Class valueProvidingClass = annotation.value();
        try {
            Method method = valueProvidingClass.getMethod("getValue", String.class);
            return method.invoke(valueProvidingClass.newInstance(), fieldString);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            throw new ConfigBuilderException(e);
        }
    }

    public Object transformValue(String fieldString, ValueProvider valueProvider) {
        Class valueProvidingClass = valueProvider.value();
        try {
            Method method = valueProvidingClass.getMethod("getValue", String.class);
            return method.invoke(valueProvidingClass.newInstance(), fieldString);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            throw new ConfigBuilderException(e);
        }
    }
}
