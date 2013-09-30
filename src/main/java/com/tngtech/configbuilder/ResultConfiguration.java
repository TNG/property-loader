package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotations.CommandLineValue;
import com.tngtech.configbuilder.annotations.DefaultValue;
import com.tngtech.configbuilder.annotations.PropertyValue;
import com.tngtech.propertyloader.PropertyLoader;
import org.apache.commons.cli.CommandLine;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Scope("prototype")
public class ResultConfiguration {

    private Properties properties;
    private CommandLine commandLineArgs;

    public ResultConfiguration() {
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
