package com.tngtech.configbuilder.annotation.propertyloaderconfiguration;


import com.google.common.collect.Lists;
import com.tngtech.propertyloader.PropertyLoader;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertiesFilesProcessor implements IPropertyLoaderConfigurationProcessor {

    public void configurePropertyLoader(Annotation annotation, PropertyLoader propertyLoader) {
        propertyLoader.withBaseNames(Lists.newArrayList(((PropertiesFiles) annotation).value()));
    }
}
