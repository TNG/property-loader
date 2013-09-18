package com.tngtech.configbuilder.annotationhandlers;


import com.tngtech.configbuilder.annotations.CommandLineValue;
import com.tngtech.configbuilder.impl.ConfigBuilderContext;
import org.springframework.beans.factory.annotation.Autowired;
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
