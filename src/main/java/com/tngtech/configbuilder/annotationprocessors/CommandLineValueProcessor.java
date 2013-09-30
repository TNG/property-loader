package com.tngtech.configbuilder.annotationprocessors;


import com.tngtech.configbuilder.ResultConfiguration;
import com.tngtech.configbuilder.annotationprocessors.interfaces.ValueExtractorProcessor;
import com.tngtech.configbuilder.annotations.CommandLineValue;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class CommandLineValueProcessor  implements ValueExtractorProcessor {

    public String getValue(Annotation annotation, ResultConfiguration resultConfiguration) {
        return resultConfiguration.getCommandLineArgs().getOptionValue(((CommandLineValue)annotation).shortOpt());
    }
}
