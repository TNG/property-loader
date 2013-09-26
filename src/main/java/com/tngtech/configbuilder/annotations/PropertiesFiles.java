package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.annotationprocessors.PropertiesFilesProcessor;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertiesFiles {
    String[] value();
    String fileExtension() default "properties";

    Class<? extends AnnotationProcessor<PropertiesFiles,ConfigBuilderContext,ConfigBuilderContext>> processor() default PropertiesFilesProcessor.class;
}
