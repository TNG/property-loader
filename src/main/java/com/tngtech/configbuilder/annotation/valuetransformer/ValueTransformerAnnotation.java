package com.tngtech.configbuilder.annotation.valuetransformer;

import com.tngtech.configbuilder.annotation.valuetransformer.IValueTransformerProcessor;

import java.lang.annotation.*;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueTransformerAnnotation {
    Class<? extends IValueTransformerProcessor<Object>> value();
}
