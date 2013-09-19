package com.tngtech.configbuilder.annotationhandlers;


import com.tngtech.configbuilder.annotations.PropertyExtension;
import com.tngtech.configbuilder.impl.ConfigBuilderContext;

import java.lang.annotation.Annotation;

public class PropertyExtensionProcessor implements AnnotationPropertyLoaderConfiguration{
    public void configurePropertyLoader(Annotation annotation, ConfigBuilderContext context){
        PropertyExtension propertyExtension = (PropertyExtension)annotation;
        String fileExtension = propertyExtension.value();
        context.getPropertyLoader().withExtension(fileExtension);
    }
}
