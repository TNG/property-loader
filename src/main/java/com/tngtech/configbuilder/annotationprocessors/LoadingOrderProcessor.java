package com.tngtech.configbuilder.annotationprocessors;

import com.tngtech.configbuilder.BuilderConfiguration;
import com.tngtech.configbuilder.annotations.LoadingOrder;
import com.tngtech.configbuilder.annotationprocessors.interfaces.BuilderConfigurationProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class LoadingOrderProcessor implements BuilderConfigurationProcessor {

    public void updateBuilderConfiguration(Annotation annotation, BuilderConfiguration context) {
        context.setAnnotationOrder(((LoadingOrder)annotation).value());
    }
}
