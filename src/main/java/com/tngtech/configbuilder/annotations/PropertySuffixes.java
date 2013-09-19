package com.tngtech.configbuilder.annotations;


public @interface PropertySuffixes {
    public String[] suffixes();
    public boolean hostnames() default false;
}
