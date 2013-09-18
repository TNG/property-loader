package com.tngtech.configbuilder.impl;

import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class ConfigBuilderContext {

    protected Properties properties;
    protected CommandLine commandLineArgs;

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

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setCommandLineArgs(CommandLine commandLineArgs) {
        this.commandLineArgs = commandLineArgs;
    }
}
