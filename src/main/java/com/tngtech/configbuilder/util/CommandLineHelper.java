package com.tngtech.configbuilder.util;

import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.configbuilder.exception.ConfigBuilderException;
import com.tngtech.configbuilder.annotation.valueextractor.CommandLineValue;
import org.apache.commons.cli.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class CommandLineHelper {

    private final BeanFactory beanFactory;
    private final AnnotationHelper annotationHelper;
    private final ErrorMessageSetup errorMessageSetup;

    @Autowired
    public CommandLineHelper(BeanFactory beanFactory, AnnotationHelper annotationHelper, ErrorMessageSetup errorMessageSetup) {
        this.beanFactory = beanFactory;
        this.annotationHelper = annotationHelper;
        this.errorMessageSetup = errorMessageSetup;
    }

    public CommandLine getCommandLine(Class configClass, String[] args) {
        Options options = getOptions(configClass);
        return parseCommandLine(args, options);
    }

    private Options getOptions(Class configClass) {
        Options options = beanFactory.getBean(Options.class);
        for (Field field : annotationHelper.getFieldsAnnotatedWith(configClass, CommandLineValue.class)) {
            options.addOption(getOption(field));
        }
        return options;
    }

    @SuppressWarnings("AccessStaticViaInstance")
    private Option getOption(Field field) {
        CommandLineValue commandLineValue = field.getAnnotation(CommandLineValue.class);
        return OptionBuilder.withLongOpt(commandLineValue.longOpt())
                .hasArg()
                .isRequired(commandLineValue.required())
                .withDescription(commandLineValue.description())
                .create(commandLineValue.shortOpt());
    }

    private CommandLine parseCommandLine(String[] args, Options options) {
        CommandLine commandLine;
        try {
            commandLine = beanFactory.getBean(CommandLineParser.class).parse(options, args);
        } catch (ParseException e) {
            throw new ConfigBuilderException(errorMessageSetup.getErrorMessage(e.getClass().getSuperclass()), e);
        }
        return commandLine;
    }
}