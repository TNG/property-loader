package com.tngtech.configbuilder.impl;

import com.tngtech.propertyloader.PropertyLoader;
import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Scope("prototype")
public class ConfigBuilderContext {

    protected Properties properties;
    protected CommandLine commandLineArgs;
    public PropertyLoader propertyLoader;

    public ConfigBuilderContext() {
        properties = new Properties();
        commandLineArgs = null;
    }

    public CommandLine getCommandLineArgs() {
        return commandLineArgs;
    }

    public Properties getProperties() {
        return properties;
    }

    public PropertyLoader getPropertyLoader() {
        return propertyLoader;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setCommandLineArgs(CommandLine commandLineArgs) {
        this.commandLineArgs = commandLineArgs;
    }

    public void setPropertyLoader(PropertyLoader propertyLoader) {
        this.propertyLoader = propertyLoader;
    }
}
