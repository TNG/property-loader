package com.tngtech.configbuilder.annotationprocessors;

import com.tngtech.configbuilder.ConfigBuilderException;
import com.tngtech.configbuilder.annotations.ValueTransformer;
import com.tngtech.configbuilder.annotationprocessors.interfaces.ValueTransformerProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class ValueProviderTransformer implements ValueTransformerProcessor<Object> {

    public Object transformString(Annotation annotation, String fieldString) {
        Class valueProvidingClass = ((ValueTransformer)annotation).value();
        try {
            Method method = valueProvidingClass.getMethod("getValue", String.class);
            return method.invoke(valueProvidingClass.newInstance(), fieldString);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            throw new ConfigBuilderException(e);
        }
    }
}
