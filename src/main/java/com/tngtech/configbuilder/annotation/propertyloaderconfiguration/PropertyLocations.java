package com.tngtech.configbuilder.annotation.propertyloaderconfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to specify the locations at which the PropertyLoader searches for properties files.<br>
 * <b>Usage:</b> <code>@PropertyLocations(directories = {"/home/user","resources"}, resourcesForClasses = {Config.class}, fromClassLoader = false)</code>
 */
@PropertyLoaderConfigurationAnnotation(PropertyLocationsProcessor.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyLocations {
    public String[] directories() default {};
    public Class[] resourcesForClasses() default {};
    public boolean fromClassLoader() default false;
}
