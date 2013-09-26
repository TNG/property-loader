package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationprocessors.ValueProviderTransformer;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;

import java.lang.annotation.*;

@ProcessedBy(ValueProviderTransformer.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueProvider {
    public Class value();

    Class<? extends AnnotationProcessor<ValueProvider,String,Object>> processor() default ValueProviderTransformer.class;
}
