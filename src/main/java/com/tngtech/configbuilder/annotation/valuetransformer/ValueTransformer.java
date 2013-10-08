package com.tngtech.configbuilder.annotation.valuetransformer;

import com.tngtech.configbuilder.FieldValueProvider;

import java.lang.annotation.*;

/**
 * This annotation uses a class that implements the {@link FieldValueProvider} interface in order to transform the value of the annotated field.<br>
 * <b>Usage:</b> <code>@ValueTransformer(value = ClassThatImplementsFieldValueProvider.class)</code>
 */
@ValueTransformerAnnotation(ValueTransformerProcessor.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueTransformer {
    public Class<? extends FieldValueProvider> value();
}
