package com.tngtech.configbuilder.annotationprocessors;


import com.google.common.collect.Lists;
import com.tngtech.configbuilder.BuilderConfiguration;
import com.tngtech.configbuilder.annotations.PropertiesFiles;
import com.tngtech.configbuilder.annotationprocessors.interfaces.BuilderConfigurationProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertiesFilesProcessor implements BuilderConfigurationProcessor {

    public void updateBuilderConfiguration(Annotation annotation, BuilderConfiguration context) {
        context.getPropertyLoader().withBaseNames(Lists.newArrayList(((PropertiesFiles)annotation).value()));
    }
}
