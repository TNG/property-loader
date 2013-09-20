package com.tngtech.configbuilder.annotationhandlers;

import com.google.common.collect.Maps;
import com.tngtech.configbuilder.annotations.PropertiesFile;
import com.tngtech.configbuilder.annotations.ValueProvider;
import com.tngtech.configbuilder.impl.ConfigBuilderContext;
import com.tngtech.configbuilder.validators.annotation.AnnotationValidatorAbstract;
import com.tngtech.configbuilder.validators.value.ValueValidatorAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;

@Component
public class AnnotationProcessor {

    private final Map<Class<? extends Annotation>, AnnotationPropertyLoaderConfiguration> propertyConfiguratorMap;

    private final Map<Class<? extends Annotation>, AnnotationValidatorAbstract> annotationValidatorMap;

    private final Map<Class<? extends Annotation>, AnnotationValueExtractor> valueExtractorMap;

    private final Map<Class<? extends Annotation>, ValueValidatorAbstract> validatorMap;

    private final ValueProviderTransformer valueProviderTransformer;

    private final AnnotationPropertiesExtractor annotationPropertiesExtractor;

    private final ConfigBuilderContext context;

    @Autowired
    public AnnotationProcessor(ValueProviderTransformer valueProviderTransformer, AnnotationPropertiesExtractor annotationPropertiesExtractor, ConfigBuilderContext context) {
        this.valueProviderTransformer = valueProviderTransformer;
        this.annotationPropertiesExtractor = annotationPropertiesExtractor;
        this.context = context;

        propertyConfiguratorMap = Maps.newHashMap();
        annotationValidatorMap = Maps.newHashMap();
        valueExtractorMap = Maps.newHashMap();
        validatorMap = Maps.newHashMap();
    }

    public void addToPropertyConfiguratorMap(Map<Class<? extends Annotation>, ? extends AnnotationPropertyLoaderConfiguration> propertyConfiguratorMap) {
        this.propertyConfiguratorMap.putAll(propertyConfiguratorMap);
    }

    public void addToValueProvidingAnnotationMap(Map<Class<? extends Annotation>, ? extends AnnotationValueExtractor> valueExtractorMap) {
        this.valueExtractorMap.putAll(valueExtractorMap);
    }

    public void addToValueValidatorMap(Map<Class<? extends Annotation>, ? extends ValueValidatorAbstract> validatorMap) {
        this.validatorMap.putAll(validatorMap);
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

    public String extractValue(Annotation annotation) {
        Class<? extends Annotation> annotationType = annotation.annotationType();

        return valueExtractorMap.containsKey(annotationType) ? valueExtractorMap.get(annotationType).getValue(annotation, context) : null;
    }

    public Object transformValue(String value, ValueProvider valueProvider) {
        return valueProviderTransformer.transformValue(value, valueProvider);
    }

    public void validateValue(Annotation annotation, String value) {
        Class<? extends Annotation> annotationType = annotation.annotationType();

        if (validatorMap.containsKey(annotationType)) {
            validatorMap.get(annotationType).validate(value);
        }
    }

    public Properties loadProperties(PropertiesFile propertiesFile) {
        return annotationPropertiesExtractor.getProperties(propertiesFile, context);
    }
}
