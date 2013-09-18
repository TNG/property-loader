package com.tngtech.configbuilder;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.annotationhandlers.AnnotationProcessor;
import com.tngtech.configbuilder.annotations.*;
import com.tngtech.configbuilder.context.Context;
import com.tngtech.configbuilder.impl.AnnotationHelper;
import com.tngtech.configbuilder.impl.ConfigBuilderContext;
import org.apache.commons.cli.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

public class ConfigBuilder<T> {

    private final AnnotationProcessor annotationProcessor;
    private final ConfigBuilderContext builderContext;
    private AnnotationHelper annotationHelper;

    private Class<T> configClass;
    private Properties errors = new Properties();
    private List<Field> fields;
    private Class[] annotationOrder = {CommandLineValue.class, PropertyValue.class, DefaultValue.class};
    private MiscFactory miscFactory;

    public ConfigBuilder(AnnotationProcessor annotationProcessor, ConfigBuilderContext builderContext, MiscFactory miscFactory, AnnotationHelper annotationHelper) {
        this.annotationProcessor = annotationProcessor;
        this.builderContext = builderContext;
        this.miscFactory = miscFactory;
        this.annotationHelper = annotationHelper;
    }

    public ConfigBuilder(AnnotationHelper annotationHelper) {
        this(Context.getBean(AnnotationProcessor.class), Context.getBean(ConfigBuilderContext.class), Context.getBean(MiscFactory.class), annotationHelper);
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
            return instanceOfConfigClass;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ConfigBuilderException(e);
        }
    }

    public ConfigBuilder<T> forClass(Class<T> configClass) {

        this.configClass = configClass;
        this.fields = Lists.newArrayList(configClass.getDeclaredFields());
        if (configClass.isAnnotationPresent(PropertiesFile.class)) {
            builderContext.setProperties(annotationHelper.loadPropertiesFromAnnotation(configClass.getAnnotation(PropertiesFile.class)));
        }
        if (configClass.isAnnotationPresent(ErrorMessageFile.class)) {
            this.errors = annotationHelper.loadPropertiesFromAnnotation(configClass.getAnnotation(ErrorMessageFile.class));
        }
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

    private T setFields(T instanceOfConfigClass) throws InstantiationException, IllegalAccessException {


        for (Field field : fields) {
            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
            validateAnnotations(declaredAnnotations);

            String value = extractValue(field);

            validateValue(annotationProcessor, declaredAnnotations, value);

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

                value = annotationProcessor.extractValue(annotation);

                if (value != null) {
                    break;
                }
            }
        }
        return value;
    }

    private void validateValue(AnnotationProcessor annotationProcessor, Annotation[] annotations, String value) {
        for (Annotation annotation : annotations) {
            annotationProcessor.validateValue(annotation, value);
        }
    }

    private void verifyCorrectTargetType(Field field, Object fieldValue) {
        if (!field.getType().isAssignableFrom(fieldValue.getClass())) {
            throw new ConfigBuilderException(String.format("cannot set field '%s' of type %s to object of type %s", field.getName(), field.getType().getName(), fieldValue.getClass().getName()));
        }
    }

}
