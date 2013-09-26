package com.tngtech.configbuilder.annotationprocessors;


import com.tngtech.configbuilder.annotations.CommandLineValue;
import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.annotations.PropertyExtension;
import com.tngtech.configbuilder.annotations.PropertyValue;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class CommandLineValueProcessor  implements AnnotationProcessor<CommandLineValue, ConfigBuilderContext, String> {

    public String process(CommandLineValue annotation, ConfigBuilderContext context) {
        return context.getCommandLineArgs().getOptionValue(annotation.shortOpt());
    }

    public String getValue(Annotation annotation, ConfigBuilderContext context) {
        CommandLineValue commandLineValue = (CommandLineValue) annotation;
        return context.getCommandLineArgs().getOptionValue(commandLineValue.shortOpt());
    }

}
