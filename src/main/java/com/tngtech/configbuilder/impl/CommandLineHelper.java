package com.tngtech.configbuilder.impl;

import com.tngtech.configbuilder.ConfigBuilderException;
import com.tngtech.configbuilder.annotations.CommandLineValue;
import org.apache.commons.cli.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class CommandLineHelper {

    private final MiscFactory miscFactory;
    private final AnnotationUtils annotationUtils;
    private final ErrorMessageSetup errorMessageSetup;

    @Autowired
    public CommandLineHelper(MiscFactory miscFactory, AnnotationUtils annotationUtils, ErrorMessageSetup errorMessageSetup) {
        this.miscFactory = miscFactory;
        this.annotationUtils = annotationUtils;
        this.errorMessageSetup = errorMessageSetup;
    }

    public CommandLine getCommandLine(Class configClass, String[] args) {
        Options options = miscFactory.createOptions();
        for (Field field : annotationUtils.getFieldsAnnotatedWith(configClass, CommandLineValue.class)) {
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
        CommandLine commandLine;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            throw new ConfigBuilderException(String.format(errorMessageSetup.getString("commandLineException")));
        }
        return commandLine;
    }
}