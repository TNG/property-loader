package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotationprocessors.interfaces.IBuilderConfigurationProcessor;
import com.tngtech.configbuilder.annotations.LoadingOrder;
import com.tngtech.configbuilder.annotations.metaannotations.PropertyLoaderConfigurationAnnotation;
import com.tngtech.configbuilder.context.Context;
import com.tngtech.configbuilder.impl.*;
import com.tngtech.propertyloader.PropertyLoader;

import java.lang.annotation.Annotation;

public class ConfigBuilder<T> {

    private final BuilderConfiguration builderConfiguration;
    private final AnnotationUtils annotationUtils;
    private final CommandLineHelper commandLineHelper;
    private final FieldSetter<T> fieldSetter;
    private final JSRValidator<T> jsrValidator;
    private final MiscFactory miscFactory;

    private Class<T> configClass;
    private String[] commandLineArgs;

    public ConfigBuilder(Class<T> configClass, BuilderConfiguration builderConfiguration, AnnotationUtils annotationUtils, CommandLineHelper commandLineHelper, JSRValidator<T> jsrValidator, FieldSetter<T> fieldSetter, MiscFactory miscFactory) {
        this.configClass = configClass;
        this.builderConfiguration = builderConfiguration;
        this.annotationUtils = annotationUtils;
        this.commandLineHelper = commandLineHelper;
        this.jsrValidator = jsrValidator;
        this.fieldSetter = fieldSetter;
        this.miscFactory = miscFactory;
    }

    public ConfigBuilder(Class<T> configClass) {
        this(configClass,
                Context.getBean(BuilderConfiguration.class),
                Context.getBean(AnnotationUtils.class),
                Context.getBean(CommandLineHelper.class),
                Context.getBean(JSRValidator.class),
                Context.getBean(FieldSetter.class),
                Context.getBean(MiscFactory.class));
    }

    public ConfigBuilder<T> withCommandLineArgs(String[] args) {

        this.commandLineArgs = args;
        return this;
    }

    public T build() {
        setupBuilderConfiguration();
        try {
            T instanceOfConfigClass = configClass.newInstance();
            fieldSetter.setFields(instanceOfConfigClass, builderConfiguration);
            jsrValidator.validate(instanceOfConfigClass);
            return instanceOfConfigClass;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ConfigBuilderException(e);
        }
    }

    private void setupBuilderConfiguration() {

        builderConfiguration.setCommandLineArgs(commandLineHelper.getCommandLine(configClass, commandLineArgs));
        builderConfiguration.setProperties(configurePropertyLoader().load());
        if(configClass.isAnnotationPresent(LoadingOrder.class)) builderConfiguration.setAnnotationOrder(configClass.getAnnotation(LoadingOrder.class).value());
    }

    private PropertyLoader configurePropertyLoader() {

        PropertyLoader propertyLoader = miscFactory.createPropertyLoader().withDefaultConfig();
        for (Annotation annotation : annotationUtils.getAnnotationsOfType(configClass.getDeclaredAnnotations(),PropertyLoaderConfigurationAnnotation.class)) {
            Class<? extends IBuilderConfigurationProcessor> processor;
            processor = annotation.annotationType().getAnnotation(PropertyLoaderConfigurationAnnotation.class).value();
            Context.getBean(processor).configurePropertyLoader(annotation, propertyLoader);
        }
        return propertyLoader;
    }
}
