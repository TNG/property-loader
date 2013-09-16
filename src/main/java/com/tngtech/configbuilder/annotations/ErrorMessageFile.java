package com.tngtech.configbuilder.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorMessageFile {
    String value() default "";
    String fileExtension() default "properties";
}
