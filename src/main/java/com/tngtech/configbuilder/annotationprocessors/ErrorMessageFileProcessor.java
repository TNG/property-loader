package com.tngtech.configbuilder.annotationprocessors;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.annotationprocessors.interfaces.IBuilderConfigurationProcessor;
import com.tngtech.configbuilder.annotations.ErrorMessageFile;
import com.tngtech.propertyloader.PropertyLoader;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class ErrorMessageFileProcessor implements IBuilderConfigurationProcessor {

    public void configurePropertyLoader(Annotation annotation, PropertyLoader propertyLoader) {
        propertyLoader.withBaseNames(Lists.newArrayList(((ErrorMessageFile) annotation).value()));
    }
}
