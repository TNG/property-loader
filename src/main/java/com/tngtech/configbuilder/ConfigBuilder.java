package com.tngtech.configbuilder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tngtech.configbuilder.annotationprocessors.interfaces.ValueExtractorProcessor;
import com.tngtech.configbuilder.annotations.*;
import com.tngtech.configbuilder.annotations.metaannotations.BuilderConfigurationAnnotation;
import com.tngtech.configbuilder.annotations.metaannotations.ValueExtractorAnnotation;
import com.tngtech.configbuilder.annotations.metaannotations.ValueTransformerAnnotation;
import com.tngtech.configbuilder.context.Context;
import com.tngtech.configbuilder.annotationprocessors.interfaces.BuilderConfigurationProcessor;
import com.tngtech.configbuilder.annotationprocessors.interfaces.ValueTransformerProcessor;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

public class ConfigBuilder<T> {

    private final static Logger log = Logger.getLogger(ConfigBuilder.class);

    private final BuilderConfiguration builderConfiguration;
    private final ResultConfiguration resultConfiguration;

    private Class<T> configClass;
    private Map<Field,List<Annotation>> fieldConfig;

    private MiscFactory miscFactory;

    public ConfigBuilder(BuilderConfiguration builderConfiguration, ResultConfiguration resultConfiguration, MiscFactory miscFactory) {
        this.builderConfiguration = builderConfiguration;
        this.resultConfiguration = resultConfiguration;
        this.miscFactory = miscFactory;
    }

    public ConfigBuilder() {
        this(Context.getBean(BuilderConfiguration.class), Context.getBean(ResultConfiguration.class), Context.getBean(MiscFactory.class));
    }

    public Class<T> getConfigClass() {
        return this.configClass;
    }

    public Map<Field,List<Annotation>> getFieldMap() {
        return fieldConfig;
    }

    public T build() {
        try {
            T instanceOfConfigClass = configClass.newInstance();
            instanceOfConfigClass = setFields(instanceOfConfigClass);
            validate(instanceOfConfigClass);
            return instanceOfConfigClass;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ConfigBuilderException(e);
        }
    }

    public ConfigBuilder<T> forClass(Class<T> configClass) {

        this.configClass = configClass;
        this.fieldConfig = Maps.newLinkedHashMap();

        fillBuilderContext();

        resultConfiguration.setProperties(builderConfiguration.getPropertyLoader().load());

        fillFieldMap();

        return this;
    }

    private void fillBuilderContext() {

        if(configClass.isAnnotationPresent(LoadingOrder.class)){
            builderConfiguration.setAnnotationOrder(configClass.getAnnotation(LoadingOrder.class).value());
        }

        builderConfiguration.setPropertyLoader(miscFactory.createPropertyLoader().withDefaultConfig());

        for (Annotation annotation : configClass.getAnnotations()) {
            Class<? extends BuilderConfigurationProcessor> processor;
            if(annotation.annotationType().isAnnotationPresent(BuilderConfigurationAnnotation.class)) {
                processor = annotation.annotationType().getAnnotation(BuilderConfigurationAnnotation.class).value();
                Context.getBean(processor).updateBuilderConfiguration(annotation, builderConfiguration);
            }
        }
    }

    private void fillFieldMap() {
        for(Field field : configClass.getDeclaredFields()) {
            List<Annotation> fieldAnnotations = Lists.newArrayList();
            Class<? extends Annotation>[] annotationOrderOfField = field.isAnnotationPresent(LoadingOrder.class) ? field.getAnnotation(LoadingOrder.class).value() : builderConfiguration.getAnnotationOrder();
            for(Class<? extends Annotation> annotationClass : annotationOrderOfField){
                if(field.isAnnotationPresent(annotationClass)){
                    fieldAnnotations.add(field.getAnnotation(annotationClass));
                }
            }
            fieldConfig.put(field,fieldAnnotations);
        }
    }

    public ConfigBuilder<T> withCommandLineArgs(String[] args) {

        Options options = miscFactory.createOptions();
        for (Field field : getAllFields(configClass, withAnnotation(CommandLineValue.class))) {
                CommandLineValue commandLineValue = field.getAnnotation(CommandLineValue.class);
                @SuppressWarnings("AccessStaticViaInstance")
                Option option = OptionBuilder.withLongOpt(commandLineValue.longOpt())
                                            .hasArg()
                                            .isRequired(commandLineValue.required())
                                            .withDescription(commandLineValue.description())
                                            .create(commandLineValue.shortOpt());
                options.addOption(option);
        }

        CommandLineParser parser = miscFactory.createCommandLineParser();
        try {
            resultConfiguration.setCommandLineArgs(parser.parse(options, args));
        } catch (ParseException e) {

        }
        return this;
    }

    private T setFields(T instanceOfConfigClass) throws IllegalAccessException {
        for (Map.Entry<Field,List<Annotation>> fieldListEntry : fieldConfig.entrySet()) {
            validateAnnotations(fieldListEntry);
            String value = extractValue(fieldListEntry);
            Object fieldValue = getTransformedFieldValueIfApplicable(fieldListEntry.getKey(), value);
            verifyCorrectTargetType(fieldListEntry.getKey(), fieldValue);
            fieldListEntry.getKey().setAccessible(true);
            fieldListEntry.getKey().set(instanceOfConfigClass, fieldValue);
        }

        return instanceOfConfigClass;
    }

    private void validateAnnotations(Map.Entry<Field,List<Annotation>> fieldListEntry) {

    }

    private String extractValue(Map.Entry<Field,List<Annotation>> fieldListEntry) {
        String value = null;

        for (Annotation annotation : fieldListEntry.getValue()) {
            Class<? extends ValueExtractorProcessor> processor;
            if(annotation.annotationType().isAnnotationPresent(ValueExtractorAnnotation.class)) {
                processor = annotation.annotationType().getAnnotation(ValueExtractorAnnotation.class).value();
                value = Context.getBean(processor).getValue(annotation, resultConfiguration);
            }

            if (value != null) break;
        }
        return value;
    }

    private Object getTransformedFieldValueIfApplicable(Field field, String value) {
        Object fieldValue = value;

        if (field.isAnnotationPresent(ValueTransformer.class))
        {
            ValueTransformer valueTransformer = field.getAnnotation(ValueTransformer.class);
            Class<? extends ValueTransformerProcessor<Object>> processor;
            processor = valueTransformer.annotationType().getAnnotation(ValueTransformerAnnotation.class).value();
            fieldValue = Context.getBean(processor).transformString(valueTransformer, value);
        }

        return fieldValue;
    }

    private void verifyCorrectTargetType(Field field, Object fieldValue) {
        if (fieldValue != null && !field.getType().isAssignableFrom(fieldValue.getClass())) {
            throw new ConfigBuilderException(String.format("cannot set field '%s' of type %s to object of type %s", field.getName(), field.getType().getName(), fieldValue.getClass().getName()));
        }
    }

    private void validate(T instanceOfConfigClass) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(instanceOfConfigClass);
        for(ConstraintViolation constraintViolation : constraintViolations){
            log.warn(constraintViolation.getMessage());
        }
    }
}
