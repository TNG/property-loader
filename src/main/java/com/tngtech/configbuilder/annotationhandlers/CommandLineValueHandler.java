package com.tngtech.configbuilder.annotationhandlers;


import com.tngtech.configbuilder.annotations.CommandLineValue;
import com.tngtech.configbuilder.impl.AnnotationHandler;

import java.lang.annotation.Annotation;

public class CommandLineValueHandler extends AnnotationHandler {
    public String getString(Annotation annotation){
        CommandLineValue commandLineValue = (CommandLineValue)annotation;
        return commandLineArgs.getOptionValue(commandLineValue.value());
    }
}
