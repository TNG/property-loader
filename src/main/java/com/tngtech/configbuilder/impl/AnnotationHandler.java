package com.tngtech.configbuilder.impl;

import org.apache.commons.cli.CommandLine;

import java.lang.annotation.Annotation;
import java.util.Properties;

public abstract class AnnotationHandler {
    protected Properties properties;
    protected CommandLine commandLineArgs;

    public void setProperties(Properties properties){
        this.properties = properties;
    }

    public void setCommandLineArgs(CommandLine commandLineArgs){
        this.commandLineArgs = commandLineArgs;
    }

    public abstract String getString(Annotation annotation);
}
