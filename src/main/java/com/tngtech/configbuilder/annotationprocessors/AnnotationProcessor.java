package com.tngtech.configbuilder.annotationprocessors;

import com.google.common.collect.Maps;
import com.tngtech.configbuilder.annotationprocessors.implementations.ValueProviderTransformer;
import com.tngtech.configbuilder.annotationprocessors.interfaces.AnnotationPropertyLoaderConfiguration;
import com.tngtech.configbuilder.annotationprocessors.interfaces.AnnotationValidatorAbstract;
import com.tngtech.configbuilder.annotationprocessors.interfaces.AnnotationValueExtractor;
import com.tngtech.configbuilder.annotations.ValueProvider;
import com.tngtech.configbuilder.ConfigBuilderContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

@Component
public class AnnotationProcessor {

    private final Map<Class<? extends Annotation>, AnnotationPropertyLoaderConfiguration> propertyConfiguratorMap;

    private final Map<Class<? extends Annotation>, AnnotationValidatorAbstract> annotationValidatorMap;

    private final Map<Class<? extends Annotation>, AnnotationValueExtractor> valueExtractorMap;

    private final ValueProviderTransformer valueProviderTransformer;

    @Autowired
    public AnnotationProcessor(ValueProviderTransformer valueProviderTransformer) {
        this.valueProviderTransformer = valueProviderTransformer;

        propertyConfiguratorMap = Maps.newHashMap();
        annotationValidatorMap = Maps.newHashMap();
        valueExtractorMap = Maps.newHashMap();
    }

    public void addToPropertyConfiguratorMap(Map<Class<? extends Annotation>, ? extends AnnotationPropertyLoaderConfiguration> propertyConfiguratorMap) {
        this.propertyConfiguratorMap.putAll(propertyConfiguratorMap);
    }

    public void addToValueProvidingAnnotationMap(Map<Class<? extends Annotation>, ? extends AnnotationValueExtractor> valueExtractorMap) {
        this.valueExtractorMap.putAll(valueExtractorMap);
    }

    public void addToAnnotationValidatorMap(Map<Class<? extends Annotation>, ? extends AnnotationValidatorAbstract> annotationValidatorMap) {
        this.annotationValidatorMap.putAll(annotationValidatorMap);
    }

    public void configurePropertyLoader(Annotation annotation, ConfigBuilderContext context) {
        Class<? extends Annotation> annotationType = annotation.annotationType();

        if (propertyConfiguratorMap.containsKey(annotationType)) {
            propertyConfiguratorMap.get(annotationType).configurePropertyLoader(annotation, context);
        }
    }

    public void validateAnnotation(Annotation annotation) {
        Class<? extends Annotation> annotationType = annotation.annotationType();

        if (annotationValidatorMap.containsKey(annotationType)) {
            annotationValidatorMap.get(annotationType).validate(annotation);
        }
    }

    public String extractValue(Annotation annotation, ConfigBuilderContext context) {
        Class<? extends Annotation> annotationType = annotation.annotationType();

        return valueExtractorMap.containsKey(annotationType) ? valueExtractorMap.get(annotationType).getValue(annotation, context) : null;
    }

    public Object transformValue(String value, ValueProvider valueProvider) {
        return valueProviderTransformer.transformValue(value, valueProvider);
    }

}
