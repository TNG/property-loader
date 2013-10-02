package com.tngtech.configbuilder.annotationprocessors;


import com.google.common.collect.Lists;
import com.tngtech.configbuilder.annotationprocessors.interfaces.IPropertyLoaderConfigurationProcessor;
import com.tngtech.configbuilder.annotations.PropertiesFiles;
import com.tngtech.propertyloader.PropertyLoader;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertiesFilesProcessor implements IPropertyLoaderConfigurationProcessor {

    public void configurePropertyLoader(Annotation annotation, PropertyLoader propertyLoader) {
        propertyLoader.withBaseNames(Lists.newArrayList(((PropertiesFiles) annotation).value()));
    }
}
