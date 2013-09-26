package com.tngtech.configbuilder.annotationprocessors;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.annotations.ErrorMessageFile;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;
import org.springframework.stereotype.Component;

@Component
public class ErrorMessageFileProcessor implements AnnotationProcessor<ErrorMessageFile,ConfigBuilderContext,ConfigBuilderContext> {

    public ConfigBuilderContext process(ErrorMessageFile annotation, ConfigBuilderContext context) {
        context.getPropertyLoader().withBaseNames(Lists.newArrayList(annotation.value()));
        return context;
    }
}
