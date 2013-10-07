package com.tngtech.configbuilder.annotation.valueextractor;

import com.tngtech.configbuilder.annotation.valueextractor.IValueExtractorProcessor;

import java.lang.annotation.*;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueExtractorAnnotation {
    Class<? extends IValueExtractorProcessor> value();
}
