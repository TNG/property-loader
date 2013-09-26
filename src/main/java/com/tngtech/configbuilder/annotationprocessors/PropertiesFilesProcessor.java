package com.tngtech.configbuilder.annotationprocessors;


import com.google.common.collect.Lists;
import com.tngtech.configbuilder.annotations.PropertiesFiles;
import com.tngtech.configbuilder.ConfigBuilderContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertiesFilesProcessor{
    public void configurePropertyLoader(Annotation annotation, ConfigBuilderContext context){
        PropertiesFiles propertyLocations = (PropertiesFiles)annotation;
        context.getPropertyLoader().withBaseNames(Lists.newArrayList(propertyLocations.value()));
    }
}
