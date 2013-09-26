package com.tngtech.configbuilder.annotationprocessors;

import com.tngtech.configbuilder.ConfigBuilderException;
import com.tngtech.configbuilder.annotations.ValueProvider;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class ValueProviderTransformer {

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
