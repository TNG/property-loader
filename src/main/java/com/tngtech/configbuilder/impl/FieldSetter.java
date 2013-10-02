package com.tngtech.configbuilder.impl;

import com.tngtech.configbuilder.ConfigBuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ResourceBundle;

@Component
public class FieldSetter<T> {
    private final FieldValueExtractor fieldValueExtractor;
    private final ErrorMessageSetup errorMessageSetup;

    @Autowired
    public FieldSetter(FieldValueExtractor fieldValueExtractor, ErrorMessageSetup errorMessageSetup) {
        this.fieldValueExtractor = fieldValueExtractor;
        this.errorMessageSetup = errorMessageSetup;
    }

    public void setFields(T instanceOfConfigClass, BuilderConfiguration builderConfiguration) {

        for (Field field : instanceOfConfigClass.getClass().getDeclaredFields()) {
            Object value = fieldValueExtractor.extractValue(field, builderConfiguration);
            try{
                verifyCorrectTargetType(field, value);
                field.setAccessible(true);
                field.set(instanceOfConfigClass, value);
            } catch(IllegalAccessException e){
                throw new ConfigBuilderException(String.format(errorMessageSetup.getString("illegalAccessExceptionSettingFields").replace("${fieldName}",field.getName()).replace("${fieldType}", field.getType().getName()).replace("${valueType}", value.getClass().getName())));

            } catch(ConfigBuilderException e){
                throw new ConfigBuilderException(String.format(errorMessageSetup.getString("targetTypeException").replace("${fieldName}",field.getName()).replace("${fieldType}",field.getType().getName()).replace("${valueType}",value.getClass().getName())));
            }
        }
    }

    private void verifyCorrectTargetType(Field field, Object value) {
        if (value != null && !field.getType().isAssignableFrom(value.getClass())) {
            throw new ConfigBuilderException("");
        }
    }
}