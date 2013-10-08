package com.tngtech.configbuilder.configuration;

import com.tngtech.configbuilder.annotation.valueextractor.CommandLineValue;
import com.tngtech.configbuilder.annotation.valueextractor.DefaultValue;
import com.tngtech.configbuilder.annotation.valueextractor.PropertyValue;
import org.apache.commons.cli.CommandLine;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Properties;

/**
 * Stores the configuration for the ConfigBuilder, i.e. the CommandLine, the Properties and the global annotation processing order.
 */
@Component
@Scope("prototype")
public class BuilderConfiguration {

    private Properties properties;
    private CommandLine commandLineArgs;
    private Class<? extends Annotation>[] annotationOrder = new Class[]{CommandLineValue.class, PropertyValue.class, DefaultValue.class};

    public BuilderConfiguration() {
        properties = new Properties();
        commandLineArgs = null;
    }

    public CommandLine getCommandLineArgs() {
        return commandLineArgs;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setCommandLine(CommandLine commandLineArgs) {
        this.commandLineArgs = commandLineArgs;
    }

    public void setAnnotationOrder(Class<? extends Annotation>[] annotationOrder) {
        this.annotationOrder = annotationOrder;
    }

    public Class<? extends Annotation>[] getAnnotationOrder(){
        return annotationOrder;
    }
}
