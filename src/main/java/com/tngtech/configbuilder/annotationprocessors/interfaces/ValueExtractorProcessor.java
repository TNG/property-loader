package com.tngtech.configbuilder.annotationprocessors.interfaces;


import com.tngtech.configbuilder.ResultConfiguration;

import java.lang.annotation.Annotation;

public interface ValueExtractorProcessor {
    public String getValue(Annotation annotation, ResultConfiguration argument);
}
