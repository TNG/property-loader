package com.tngtech.configbuilder.annotation.valuetransformer;

import com.tngtech.configbuilder.FieldValueProvider;
import com.tngtech.configbuilder.exception.ValueTransformerException;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class ValueTransformerProcessor implements IValueTransformerProcessor<Object> {

    public Object transformString(Annotation annotation, String fieldString) {
        Class<? extends  FieldValueProvider> valueProvidingClass = ((ValueTransformer)annotation).value();
        try {
            return valueProvidingClass.newInstance().getValue(fieldString);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new ValueTransformerException(e);
        }
    }
}
