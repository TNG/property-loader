package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.ErrorMessageFile;
import com.tngtech.configbuilder.annotation.configuration.LoadingOrder;
import com.tngtech.configbuilder.configuration.BuilderConfiguration;
import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.configbuilder.context.Context;
import com.tngtech.configbuilder.exception.ConfigBuilderException;
import com.tngtech.configbuilder.util.*;
import com.tngtech.propertyloader.PropertyLoader;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Builds a config object.
 * ConfigBuilder instantiates a class and sets fields of the instance by parsing annotations and
 * loading values from properties files or the command line. It validates the instance by parsing JSR303 constraint annotations.<p>
 *
 * Fields of the config class can have the following annotations:<br>
 * {@link com.tngtech.configbuilder.annotation.valueextractor.DefaultValue}<br>
 * {@link com.tngtech.configbuilder.annotation.valueextractor.PropertyValue}<br>
 * {@link com.tngtech.configbuilder.annotation.valueextractor.CommandLineValue}<br>
 * {@link com.tngtech.configbuilder.annotation.valuetransformer.ValueTransformer}<br>
 * {@link LoadingOrder}<p>
 *
 * Properties files are loaded with a PropertyLoader using its default config. In order to change settings for the PropertyLoader, the config class may be annotated with<br>
 * {@link com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertiesFiles}<br>
 * {@link com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyLocations}<br>
 * {@link com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertySuffixes}<br>
 * {@link com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyExtension}<p>
 *
 * To specify a global order for parsing {@link com.tngtech.configbuilder.annotation.valueextractor.ValueExtractorAnnotation} annotations, annotate the class with <br>
 * {@link LoadingOrder}<p>
 *
 * To specify your own error messages file (which is loaded by the PropertyLoader with the same settings as other the properties files), annotate the class with <br>
 * {@link ErrorMessageFile}<p>
 *
 * @author Matthias Bollwein
 * @version 0.1-SNAPSHOT
 * @param <T> The type of the config class which shall be instantiated.
 */
public class ConfigBuilder<T> {

    private final static Logger log = Logger.getLogger(ConfigBuilder.class);

    private final BuilderConfiguration builderConfiguration;
    private final PropertyLoaderConfigurator propertyLoaderConfigurator;
    private final CommandLineHelper commandLineHelper;
    private final FieldSetter<T> fieldSetter;
    private final JSRValidator<T> jsrValidator;
    private final ErrorMessageSetup errorMessageSetup;
    private final ConstructionHelper<T> constructionHelper;

    private Class<T> configClass;
    private String[] commandLineArgs;

    private ConfigBuilder(Class<T> configClass, BuilderConfiguration builderConfiguration, PropertyLoaderConfigurator propertyLoaderConfigurator, CommandLineHelper commandLineHelper, JSRValidator<T> jsrValidator, FieldSetter<T> fieldSetter, ErrorMessageSetup errorMessageSetup, ConstructionHelper<T> constructionHelper) {
        this.configClass = configClass;
        this.builderConfiguration = builderConfiguration;
        this.propertyLoaderConfigurator = propertyLoaderConfigurator;
        this.commandLineHelper = commandLineHelper;
        this.jsrValidator = jsrValidator;
        this.fieldSetter = fieldSetter;
        this.errorMessageSetup = errorMessageSetup;
        this.constructionHelper = constructionHelper;
    }

    /**
     *
     * @param configClass The config class of which an instance shall be built.
     */
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

    /**
     * Sets the command line arguments that the ConfigBuilder uses in order to parse fields annotated with <code>@CommandLineValue</code>.
     * Command line arguments must match the options specified in the <code>@CommandLineValue</code> annotations.
     * @param args the command line arguments
     * @return the instance of ConfigBuilder
     */
    public ConfigBuilder<T> withCommandLineArgs(String[] args) {
        this.commandLineArgs = args;
        return this;
    }

    /**
     * Gets an instance of the config, sets the fields and validates them.
     * The method sets up the configuration by loading properties and parsing command line arguments,
     * then tries to find a constructor of the config class that matches the arguments passed to it,
     * instantiates the config class, sets its fields and validates the instance.
     * @param objects a vararg of Objects passed to a corresponding constructor of the config class.
     * @return An instance of the config class.
     */
    public T build(Object... objects) {
        PropertyLoader propertyLoader = propertyLoaderConfigurator.configurePropertyLoader(configClass);
        setupBuilderConfiguration(propertyLoader);
        initializeErrorMessageSetup(propertyLoader);
        T instanceOfConfigClass = constructionHelper.getInstance(configClass, objects);
        fieldSetter.setFields(instanceOfConfigClass, builderConfiguration);
        jsrValidator.validate(instanceOfConfigClass);
        return instanceOfConfigClass;
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
        String errorMessageFile = configClass.isAnnotationPresent(ErrorMessageFile.class) ? configClass.getAnnotation(ErrorMessageFile.class).value() : null;
        errorMessageSetup.initialize(errorMessageFile, propertyLoader);
    }
}