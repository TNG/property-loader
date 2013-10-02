package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotations.ErrorMessageFile;
import com.tngtech.configbuilder.annotations.LoadingOrder;
import com.tngtech.configbuilder.context.Context;
import com.tngtech.configbuilder.impl.*;
import com.tngtech.propertyloader.PropertyLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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

    public T build(Object... objects) {
        setupBuilderConfigurationAndErrorMessages();
        T instanceOfConfigClass = instantiateConfig(objects);
        fieldSetter.setFields(instanceOfConfigClass, builderConfiguration);
        jsrValidator.validate(instanceOfConfigClass);
        return instanceOfConfigClass;
    }

    private T instantiateConfig(Object[] objects) {
        try {
            Constructor[] constructors = configClass.getDeclaredConstructors();
            for(Constructor<T> constructor : constructors){
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                if(parameterTypes.length != objects.length) break;
                boolean isConstructor = true;
                for(int i=0; i < parameterTypes.length; i++){
                    isConstructor = isConstructor && parameterTypes[i].isAssignableFrom(objects[i].getClass());
                }
                if(isConstructor) {
                    return constructor.newInstance(objects);
                }
            }
            throw new ConfigBuilderException(errorMessageSetup.getString("noSuitableConstructorFound"));
        } catch (InstantiationException e) {
            throw new ConfigBuilderException(errorMessageSetup.getString("instantiationException") + e);
        }catch (IllegalAccessException e) {
            throw new ConfigBuilderException(errorMessageSetup.getString("illegalAccessExceptionInstantiatingConfig") + e);
        }catch (InvocationTargetException e) {
            throw new ConfigBuilderException(errorMessageSetup.getString("InvocationTargetException") + e);
        }
    }


    private void setupBuilderConfigurationAndErrorMessages() {
        if(configClass.isAnnotationPresent(LoadingOrder.class)) builderConfiguration.setAnnotationOrder(configClass.getAnnotation(LoadingOrder.class).value());
        builderConfiguration.setCommandLine(commandLineHelper.getCommandLine(configClass, commandLineArgs));
        PropertyLoader propertyLoader = propertyLoaderConfigurator.configurePropertyLoader(configClass);
        builderConfiguration.setProperties(propertyLoader.load());
        String errorMessageFile = configClass.isAnnotationPresent(ErrorMessageFile.class) ? configClass.getAnnotation(ErrorMessageFile.class).value() : "errors";
        errorMessageSetup.initialize(errorMessageFile, propertyLoader);
    }
}
