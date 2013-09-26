package com.tngtech.configbuilder.annotationprocessors.implementations;


import com.tngtech.configbuilder.annotations.CommandLineValue;
import com.tngtech.configbuilder.ConfigBuilderContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class CommandLineValueProcessor {

    public String getValue(Annotation annotation, ConfigBuilderContext context) {
        CommandLineValue commandLineValue = (CommandLineValue) annotation;
        return context.getCommandLineArgs().getOptionValue(commandLineValue.shortOpt());
    }

}
