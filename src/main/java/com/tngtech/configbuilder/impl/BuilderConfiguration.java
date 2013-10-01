package com.tngtech.configbuilder.impl;

import com.tngtech.configbuilder.annotations.CommandLineValue;
import com.tngtech.configbuilder.annotations.DefaultValue;
import com.tngtech.configbuilder.annotations.PropertyValue;
import org.apache.commons.cli.CommandLine;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.Properties;

@Component
@Scope("prototype")
public class BuilderConfiguration {

    private Properties properties;
    private CommandLine commandLineArgs;
    private Class[] annotationOrder = new Class[]{CommandLineValue.class, PropertyValue.class, DefaultValue.class};

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

    public void setCommandLineArgs(CommandLine commandLineArgs) {
        this.commandLineArgs = commandLineArgs;
    }

    public void setAnnotationOrder(Class[] annotationOrder) {
        this.annotationOrder = annotationOrder;
    }

    public Class[] getAnnotationOrder(){
        return annotationOrder;
    }
}
