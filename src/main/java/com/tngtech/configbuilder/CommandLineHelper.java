package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotations.CommandLineValue;
import org.apache.commons.cli.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class CommandLineHelper {

    private final MiscFactory miscFactory;
    private final AnnotationUtils annotationUtils;

    @Autowired
    public CommandLineHelper(MiscFactory miscFactory, AnnotationUtils annotationUtils) {
        this.miscFactory = miscFactory;
        this.annotationUtils = annotationUtils;
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
            throw new ConfigBuilderException("unable to parse command line arguments");
        }
        return commandLine;
    }
}