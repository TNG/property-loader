package com.tngtech.configbuilder.annotation.valueextractor;


import com.tngtech.configbuilder.configuration.BuilderConfiguration;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class CommandLineValueProcessor  implements IValueExtractorProcessor {

    public String getValue(Annotation annotation, BuilderConfiguration builderConfiguration) {
        return builderConfiguration.getCommandLine().getOptionValue(((CommandLineValue)annotation).shortOpt());
    }
}
