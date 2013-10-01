package com.tngtech.configbuilder.annotationprocessors;

import com.tngtech.configbuilder.annotationprocessors.interfaces.IBuilderConfigurationProcessor;
import com.tngtech.configbuilder.annotations.PropertyExtension;
import com.tngtech.propertyloader.PropertyLoader;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertyExtensionProcessor implements IBuilderConfigurationProcessor {

    public void configurePropertyLoader(Annotation annotation, PropertyLoader propertyLoader) {
        String fileExtension = ((PropertyExtension)annotation).value();
        propertyLoader.withExtension(fileExtension);
    }
}
