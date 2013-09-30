package com.tngtech.configbuilder.annotationprocessors;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.BuilderConfiguration;
import com.tngtech.configbuilder.annotations.ErrorMessageFile;
import com.tngtech.configbuilder.annotationprocessors.interfaces.BuilderConfigurationProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class ErrorMessageFileProcessor implements BuilderConfigurationProcessor {

    public void updateBuilderConfiguration(Annotation annotation, BuilderConfiguration builderConfiguration) {
        builderConfiguration.getPropertyLoader().withBaseNames(Lists.newArrayList(((ErrorMessageFile)annotation).value()));
    }
}
