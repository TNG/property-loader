package com.tngtech.configbuilder.annotationprocessors;

import com.tngtech.configbuilder.annotations.PropertyExtension;
import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertyExtensionProcessor implements AnnotationProcessor<PropertyExtension, ConfigBuilderContext, ConfigBuilderContext> {

    public ConfigBuilderContext process(PropertyExtension annotation, ConfigBuilderContext context) {
        String fileExtension = annotation.value();
        context.getPropertyLoader().withExtension(fileExtension);
        return context;
    }

    public void configurePropertyLoader(Annotation annotation, ConfigBuilderContext context){
        PropertyExtension propertyExtension = (PropertyExtension)annotation;
        String fileExtension = propertyExtension.value();
        context.getPropertyLoader().withExtension(fileExtension);
    }
}
