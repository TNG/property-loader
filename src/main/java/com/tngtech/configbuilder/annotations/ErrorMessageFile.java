package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationprocessors.PropertiesFilesProcessor;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorMessageFile {
    String value();
    String fileExtension() default "properties";

    Class<?> processor() default PropertiesFilesProcessor.class;

}
