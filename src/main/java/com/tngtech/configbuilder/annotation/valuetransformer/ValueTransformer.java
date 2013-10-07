package com.tngtech.configbuilder.annotation.valuetransformer;

import com.tngtech.configbuilder.annotation.valuetransformer.ValueTransformerProcessor;
import com.tngtech.configbuilder.annotation.valuetransformer.ValueTransformerAnnotation;
import com.tngtech.configbuilder.FieldValueProvider;

import java.lang.annotation.*;

@ValueTransformerAnnotation(ValueTransformerProcessor.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueTransformer {
    public Class<? extends FieldValueProvider> value();
}
