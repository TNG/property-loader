package com.tngtech.configbuilder.annotationprocessors;

import com.tngtech.configbuilder.BuilderConfiguration;
import com.tngtech.configbuilder.annotations.PropertyExtension;
import com.tngtech.configbuilder.annotationprocessors.interfaces.BuilderConfigurationProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertyExtensionProcessor implements BuilderConfigurationProcessor {

    public void updateBuilderConfiguration(Annotation annotation, BuilderConfiguration context) {
        String fileExtension = ((PropertyExtension)annotation).value();
        context.getPropertyLoader().withExtension(fileExtension);
    }
}
