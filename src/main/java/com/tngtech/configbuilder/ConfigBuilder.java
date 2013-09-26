package com.tngtech.configbuilder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tngtech.configbuilder.annotations.*;
import com.tngtech.configbuilder.context.Context;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;
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

    private final ConfigBuilderContext builderContext;

    private Class<T> configClass;
    private Map<Field,List<Annotation>> fieldConfig;

    private MiscFactory miscFactory;

    public ConfigBuilder(ConfigBuilderContext builderContext, MiscFactory miscFactory) {
        this.builderContext = builderContext;
        this.miscFactory = miscFactory;
    }

    public ConfigBuilder() {
        this(Context.getBean(ConfigBuilderContext.class), Context.getBean(MiscFactory.class));
    }

    public Class<T> getConfigClass() {
        return this.configClass;
    }

    public Map<Field,List<Annotation>> getFields() {
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

        fillFieldMap();
        fillBuilderContext();

        return this;
    }

    private void fillBuilderContext() {
        builderContext.setPropertyLoader(miscFactory.createPropertyLoader().withDefaultConfig());
        for (Annotation annotation : configClass.getAnnotations()) {
            Class<? extends AnnotationProcessor<Annotation,ConfigBuilderContext,ConfigBuilderContext>> processor = null;
            try {
                processor = (Class)annotation.annotationType().getMethod("processor").invoke(annotation);
                processor.newInstance().process(annotation, builderContext);
            }
            catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {

            }
        }
        builderContext.setProperties(builderContext.getPropertyLoader().load());
    }

    private void fillFieldMap() {
        for(Field field : configClass.getDeclaredFields()) {
            List<Annotation> fieldAnnotations = Lists.newArrayList();
            Class<? extends Annotation>[] annotationOrderOfField = field.isAnnotationPresent(LoadingOrder.class) ? field.getAnnotation(LoadingOrder.class).value() : builderContext.annotationOrder;
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
            builderContext.setCommandLineArgs(parser.parse(options, args));
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

                Class<? extends AnnotationProcessor<Annotation,ConfigBuilderContext,String>> processor = null;
                try {
                    processor = (Class)annotation.annotationType().getMethod("processor").invoke(annotation);
                    value = processor.newInstance().process(annotation, builderContext);
                }
                catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {}

                if (value != null) {
                    break;
                }

        }
        return value;
    }

    private Object getTransformedFieldValueIfApplicable(Field field, String value) {
        Object fieldValue = value;

        if (field.isAnnotationPresent(ValueProvider.class))
        {
            ValueProvider valueProvider = field.getAnnotation(ValueProvider.class);
            Class<? extends AnnotationProcessor<ValueProvider,String,Object>> processor = null;
            try {
                processor = valueProvider.processor();
                fieldValue = processor.newInstance().process(valueProvider, value);
            }
            catch (InstantiationException | IllegalAccessException e) {

            }
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
