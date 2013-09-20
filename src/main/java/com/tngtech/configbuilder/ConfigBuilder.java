package com.tngtech.configbuilder;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.annotationhandlers.AnnotationProcessor;
import com.tngtech.configbuilder.annotations.*;
import com.tngtech.configbuilder.annotations.config.PropertyLoaderConfigurator;
import com.tngtech.configbuilder.context.Context;
import com.tngtech.configbuilder.impl.ConfigBuilderContext;
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

    private final AnnotationProcessor annotationProcessor;
    private final ConfigBuilderContext builderContext;

    private Class<T> configClass;
    private List<Field> fields;
    private Class[] annotationOrder = {CommandLineValue.class, PropertyValue.class, DefaultValue.class};
    private MiscFactory miscFactory;

    public ConfigBuilder(AnnotationProcessor annotationProcessor, ConfigBuilderContext builderContext, MiscFactory miscFactory) {
        this.annotationProcessor = annotationProcessor;
        this.builderContext = builderContext;
        this.miscFactory = miscFactory;
    }

    public ConfigBuilder() {
        this(Context.getBean(AnnotationProcessor.class), Context.getBean(ConfigBuilderContext.class), Context.getBean(MiscFactory.class));
    }

    public Class<T> getConfigClass() {
        return this.configClass;
    }

    public List<Field> getFields() {
        return this.fields;
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
        this.fields = Lists.newArrayList(configClass.getDeclaredFields());
        builderContext.setPropertyLoader(miscFactory.createPropertyLoader().withDefaultConfig());

        Annotation[] annotations = configClass.getAnnotations();
        for (Annotation annotation : annotations) {
            Annotation propertyLoaderConfigAnnotation = annotation.annotationType().getAnnotation(PropertyLoaderConfigurator.class);
            if (propertyLoaderConfigAnnotation != null) {
                annotationProcessor.configurePropertyLoader(annotation, builderContext);
            }
        }

        builderContext.setProperties(builderContext.getPropertyLoader().loadProperties());

        if (configClass.isAnnotationPresent(LoadingOrder.class)) {
            this.annotationOrder = configClass.getAnnotation(LoadingOrder.class).value();
        }

        return this;
    }

    public ConfigBuilder<T> withCommandLineArgs(String[] args) {

        Options options = miscFactory.createOptions();
        for (Field field : getAllFields(configClass, withAnnotation(CommandLineValue.class))) {
                CommandLineValue commandLineValue = field.getAnnotation(CommandLineValue.class);
                options.addOption(commandLineValue.value(), true, commandLineValue.description());
        }

        CommandLineParser parser = miscFactory.createCommandLineParser();
        try {
            builderContext.setCommandLineArgs(parser.parse(options, args));
        } catch (ParseException e) {
        }
        return this;
    }

    private void validate(T instanceOfConfigClass) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(instanceOfConfigClass);
        for(ConstraintViolation constraintViolation : constraintViolations){
            log.warn(constraintViolation.getMessage());
        }
    }

    private T setFields(T instanceOfConfigClass) throws InstantiationException, IllegalAccessException {


        for (Field field : fields) {
            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
            validateAnnotations(declaredAnnotations);

            String value = extractValue(field);

            Object fieldValue = getTransformedFieldValueIfApplicable(field, value);

            verifyCorrectTargetType(field, fieldValue);

            field.setAccessible(true);
            field.set(instanceOfConfigClass, fieldValue);
        }

        return instanceOfConfigClass;
     }

    private Object getTransformedFieldValueIfApplicable(Field field, String value) {
        Object fieldValue = value;

        if (field.isAnnotationPresent(ValueProvider.class))
        {
            ValueProvider valueProvider = field.getAnnotation(ValueProvider.class);
            fieldValue = annotationProcessor.transformValue(value, valueProvider);
        }

        return fieldValue;
    }

    private void validateAnnotations(Annotation[] declaredAnnotations) {
        for (Annotation annotation : declaredAnnotations) {
            annotationProcessor.validateAnnotation(annotation);
        }
    }

    private List<Class> getOrderedValueAnnotations(Field field) {
        List<Class> fieldAnnotationOrder = Lists.newArrayList(annotationOrder);
        if (field.isAnnotationPresent(LoadingOrder.class)) {
            fieldAnnotationOrder = Lists.newArrayList(field.getAnnotation(LoadingOrder.class).value());
        }
        return fieldAnnotationOrder;
    }

    private String extractValue(Field field) {
        String value = null;

        for (Class annotationClass : getOrderedValueAnnotations(field)) {
            if (field.isAnnotationPresent(annotationClass)) {
                Annotation annotation = field.getAnnotation(annotationClass);

                value = annotationProcessor.extractValue(annotation, builderContext);

                if (value != null) {
                    break;
                }
            }
        }
        return value;
    }

    private void verifyCorrectTargetType(Field field, Object fieldValue) {
        if (fieldValue != null && !field.getType().isAssignableFrom(fieldValue.getClass())) {
            throw new ConfigBuilderException(String.format("cannot set field '%s' of type %s to object of type %s", field.getName(), field.getType().getName(), fieldValue.getClass().getName()));
        }
    }

}
