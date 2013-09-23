package com.tngtech.configbuilder.annotationprocessors.implementations;


import com.tngtech.configbuilder.annotationprocessors.interfaces.AnnotationValueExtractor;
import com.tngtech.configbuilder.annotations.CommandLineValue;
import com.tngtech.configbuilder.ConfigBuilderContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class CommandLineValueProcessor implements AnnotationValueExtractor {

    @Override
    public String getValue(Annotation annotation, ConfigBuilderContext context) {
        CommandLineValue commandLineValue = (CommandLineValue) annotation;
        return context.getCommandLineArgs().getOptionValue(commandLineValue.value());
    }

}
