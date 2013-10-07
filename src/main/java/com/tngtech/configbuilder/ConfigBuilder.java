package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.ErrorMessageFile;
import com.tngtech.configbuilder.annotation.configuration.LoadingOrder;
import com.tngtech.configbuilder.configuration.BuilderConfiguration;
import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.configbuilder.context.Context;
import com.tngtech.configbuilder.exception.ConfigBuilderException;
import com.tngtech.configbuilder.util.*;
import com.tngtech.propertyloader.PropertyLoader;

import java.lang.reflect.InvocationTargetException;

public class ConfigBuilder<T> {

    private final BuilderConfiguration builderConfiguration;
    private final PropertyLoaderConfigurator propertyLoaderConfigurator;
    private final CommandLineHelper commandLineHelper;
    private final FieldSetter<T> fieldSetter;
    private final JSRValidator<T> jsrValidator;
    private final ErrorMessageSetup errorMessageSetup;
    private final ConstructionHelper<T> constructionHelper;

    private Class<T> configClass;
    private String[] commandLineArgs;

    public ConfigBuilder(Class<T> configClass, BuilderConfiguration builderConfiguration, PropertyLoaderConfigurator propertyLoaderConfigurator, CommandLineHelper commandLineHelper, JSRValidator<T> jsrValidator, FieldSetter<T> fieldSetter, ErrorMessageSetup errorMessageSetup, ConstructionHelper<T> constructionHelper) {
        this.configClass = configClass;
        this.builderConfiguration = builderConfiguration;
        this.propertyLoaderConfigurator = propertyLoaderConfigurator;
        this.commandLineHelper = commandLineHelper;
        this.jsrValidator = jsrValidator;
        this.fieldSetter = fieldSetter;
        this.errorMessageSetup = errorMessageSetup;
        this.constructionHelper = constructionHelper;
    }

    public ConfigBuilder(Class<T> configClass) {
        this(configClass,
                Context.getBean(BuilderConfiguration.class),
                Context.getBean(PropertyLoaderConfigurator.class),
                Context.getBean(CommandLineHelper.class),
                Context.getBean(JSRValidator.class),
                Context.getBean(FieldSetter.class),
                Context.getBean(ErrorMessageSetup.class),
                Context.getBean(ConstructionHelper.class));
    }

    public ConfigBuilder<T> withCommandLineArgs(String[] args) {

        this.commandLineArgs = args;
        return this;
    }

    public T build(Object... objects) {
        try {
            PropertyLoader propertyLoader = propertyLoaderConfigurator.configurePropertyLoader(configClass);
            setupBuilderConfiguration(propertyLoader);
            initializeErrorMessageSetup(propertyLoader);
            T instanceOfConfigClass = constructionHelper.findSuitableConstructor(configClass, objects).newInstance(objects);
            fieldSetter.setFields(instanceOfConfigClass, builderConfiguration);
            jsrValidator.validate(instanceOfConfigClass);
            return instanceOfConfigClass;
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ConfigBuilderException(errorMessageSetup.getErrorMessage(e), e);
        }
    }

    private void setupBuilderConfiguration(PropertyLoader propertyLoader) {
        if (configClass.isAnnotationPresent(LoadingOrder.class)){
            builderConfiguration.setAnnotationOrder(configClass.getAnnotation(LoadingOrder.class).value());
        }
        builderConfiguration.setCommandLine(commandLineHelper.getCommandLine(configClass, commandLineArgs));

        builderConfiguration.setProperties(propertyLoader.load());
        initializeErrorMessageSetup(propertyLoader);
    }

    private void initializeErrorMessageSetup(PropertyLoader propertyLoader) {
        String errorMessageFile = configClass.isAnnotationPresent(ErrorMessageFile.class) ? configClass.getAnnotation(ErrorMessageFile.class).value() : "errors";
        errorMessageSetup.initialize(errorMessageFile, propertyLoader);
    }

}