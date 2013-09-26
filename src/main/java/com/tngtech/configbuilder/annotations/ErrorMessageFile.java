package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.annotationprocessors.ErrorMessageFileProcessor;
import com.tngtech.configbuilder.annotationprocessors.PropertiesFilesProcessor;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorMessageFile {
    String value();
    String fileExtension() default "properties";

    Class<? extends AnnotationProcessor<ErrorMessageFile,ConfigBuilderContext,ConfigBuilderContext>> processor() default ErrorMessageFileProcessor.class;

}
