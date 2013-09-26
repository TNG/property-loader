package com.tngtech.configbuilder;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.annotations.*;
import com.tngtech.configbuilder.context.Context;
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
    private List<Field> fields;
    private Class[] annotationOrder = {CommandLineValue.class, PropertyValue.class, DefaultValue.class};
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
            Class<?> processor = null;
            try {
                processor = (Class)annotation.annotationType().getMethod("processor").invoke(annotation);
                processor.getMethod("configurePropertyLoader",Annotation.class,ConfigBuilderContext.class).invoke(processor.newInstance(),annotation,builderContext);
                //processor.newInstance().configurePropertyLoader(annotation, builderContext);
            }
            catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            }
        }



        builderContext.setProperties(builderContext.getPropertyLoader().load());

        if (configClass.isAnnotationPresent(LoadingOrder.class)) {
            this.annotationOrder = configClass.getAnnotation(LoadingOrder.class).value();
        }

        return this;
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
            Class<?> processor = null;
            try {
                processor = (Class)valueProvider.annotationType().getDeclaredMethod("processor").invoke(valueProvider);
                fieldValue = processor.getMethod("transformValue",String.class,ValueProvider.class).invoke(processor.newInstance(),value,valueProvider);
                //fieldValue = processor.newInstance().transformValue(value, valueProvider);
            }
            catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {

            }
        }

        return fieldValue;
    }

    private void validateAnnotations(Annotation[] declaredAnnotations) {
        for (Annotation annotation : declaredAnnotations) {

        }
    }

    private List<Class> getOrderedValueAnnotations(Field field) {
        if (field.isAnnotationPresent(LoadingOrder.class)) {
            return Lists.newArrayList(field.getAnnotation(LoadingOrder.class).value());
        }
        else return Lists.newArrayList(annotationOrder);
    }

    private String extractValue(Field field) {
        String value = null;

        for (Class annotationClass : getOrderedValueAnnotations(field)) {
            if (field.isAnnotationPresent(annotationClass)) {
                Annotation annotation = field.getAnnotation(annotationClass);

                Class<?> processor = null;
                try {
                    processor = (Class)annotation.annotationType().getMethod("processor").invoke(annotation);
                    value = (String)processor.getMethod("getValue",Annotation.class,ConfigBuilderContext.class).invoke(processor.newInstance(),annotation,builderContext);
                    //value = processor.newInstance().getValue(annotation, builderContext);
                }
                catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {}

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
