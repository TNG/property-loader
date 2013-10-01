package com.tngtech.configbuilder.impl;

import com.tngtech.configbuilder.ConfigBuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class FieldSetter<T> {
    private final FieldValueExtractor fieldValueExtractor;

    @Autowired
    public FieldSetter(FieldValueExtractor fieldValueExtractor) {
        this.fieldValueExtractor = fieldValueExtractor;
    }

    public void setFields(T instanceOfConfigClass, BuilderConfiguration builderConfiguration) throws IllegalAccessException {

        for (Field field : instanceOfConfigClass.getClass().getDeclaredFields()) {
            Object value = fieldValueExtractor.extractValue(field, builderConfiguration);
            verifyCorrectTargetType(field, value);
            field.setAccessible(true);
            field.set(instanceOfConfigClass, value);
        }
    }

    private void verifyCorrectTargetType(Field field, Object fieldValue) {
        if (fieldValue != null && !field.getType().isAssignableFrom(fieldValue.getClass())) {
            throw new ConfigBuilderException(String.format("cannot set field '%s' of type %s to object of type %s", field.getName(), field.getType().getName(), fieldValue.getClass().getName()));
        }
    }
}