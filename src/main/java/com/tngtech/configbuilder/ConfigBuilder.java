package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotations.ErrorMessageFile;
import com.tngtech.configbuilder.annotations.LoadingOrder;
import com.tngtech.configbuilder.context.Context;
import com.tngtech.configbuilder.impl.*;

public class ConfigBuilder<T> {

    private final BuilderConfiguration builderConfiguration;
    private final PropertyLoaderConfigurator propertyLoaderConfigurator;
    private final CommandLineHelper commandLineHelper;
    private final FieldSetter<T> fieldSetter;
    private final JSRValidator<T> jsrValidator;
    private final ErrorMessageSetup errorMessageSetup;

    private Class<T> configClass;
    private String[] commandLineArgs;

    public ConfigBuilder(Class<T> configClass, BuilderConfiguration builderConfiguration, PropertyLoaderConfigurator propertyLoaderConfigurator, CommandLineHelper commandLineHelper, JSRValidator<T> jsrValidator, FieldSetter<T> fieldSetter, ErrorMessageSetup errorMessageSetup) {
        this.configClass = configClass;
        this.builderConfiguration = builderConfiguration;
        this.propertyLoaderConfigurator = propertyLoaderConfigurator;
        this.commandLineHelper = commandLineHelper;
        this.jsrValidator = jsrValidator;
        this.fieldSetter = fieldSetter;
        this.errorMessageSetup = errorMessageSetup;

        if(configClass.isAnnotationPresent(ErrorMessageFile.class)) this.errorMessageSetup.initialize(configClass.getAnnotation(ErrorMessageFile.class).value());
    }

    public ConfigBuilder(Class<T> configClass) {
        this(configClass,
                Context.getBean(BuilderConfiguration.class),
                Context.getBean(PropertyLoaderConfigurator.class),
                Context.getBean(CommandLineHelper.class),
                Context.getBean(JSRValidator.class),
                Context.getBean(FieldSetter.class),
                Context.getBean(ErrorMessageSetup.class));
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
        } catch (InstantiationException e) {
            throw new ConfigBuilderException(errorMessageSetup.getString("instantiationException") + e);
        }
        catch (IllegalAccessException e) {
            throw new ConfigBuilderException(errorMessageSetup.getString("illegalAccessExceptionInstantiatingConfig") + e);
        }
    }

    private void setupBuilderConfiguration() {
        if(configClass.isAnnotationPresent(LoadingOrder.class)) builderConfiguration.setAnnotationOrder(configClass.getAnnotation(LoadingOrder.class).value());
        builderConfiguration.setCommandLine(commandLineHelper.getCommandLine(configClass, commandLineArgs));
        builderConfiguration.setProperties(propertyLoaderConfigurator.configurePropertyLoader(configClass).load());
    }
}
