package com.tngtech.configbuilder;

import com.tngtech.propertyloader.PropertyLoader;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class MiscFactory {
    public CommandLineParser createCommandLineParser() {
        return new GnuParser();
    }

    public Options createOptions() {
        return new Options();
    }

    public PropertyLoader createPropertyLoader() {
        return new PropertyLoader();
    }
}
