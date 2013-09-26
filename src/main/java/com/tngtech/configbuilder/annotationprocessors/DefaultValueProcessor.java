package com.tngtech.configbuilder.annotationprocessors;

import com.tngtech.configbuilder.annotations.DefaultValue;
import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.annotations.PropertyExtension;
import com.tngtech.configbuilder.annotations.PropertyValue;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class DefaultValueProcessor  implements AnnotationProcessor<DefaultValue, ConfigBuilderContext, String> {

    public String process(DefaultValue annotation, ConfigBuilderContext context) {
        return annotation.value();
    }

    public String getValue(Annotation annotation, ConfigBuilderContext context) {
        DefaultValue defaultValue = (DefaultValue) annotation;
        return defaultValue.value();
    }
}
