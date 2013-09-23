package com.tngtech.configbuilder.annotationprocessors.implementations;


import com.tngtech.configbuilder.annotationprocessors.interfaces.AnnotationPropertyLoaderConfiguration;
import com.tngtech.configbuilder.annotations.PropertyExtension;
import com.tngtech.configbuilder.ConfigBuilderContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertyExtensionProcessor implements AnnotationPropertyLoaderConfiguration {
    public void configurePropertyLoader(Annotation annotation, ConfigBuilderContext context){
        PropertyExtension propertyExtension = (PropertyExtension)annotation;
        String fileExtension = propertyExtension.value();
        context.getPropertyLoader().withExtension(fileExtension);
    }
}
