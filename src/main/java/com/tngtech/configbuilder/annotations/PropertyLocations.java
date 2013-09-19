package com.tngtech.configbuilder.annotations;

public @interface PropertyLocations {
    public String[] directories();
    public Class[] resourcesforclasses();
}
