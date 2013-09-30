package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotations.CommandLineValue;
import com.tngtech.configbuilder.annotations.DefaultValue;
import com.tngtech.configbuilder.annotations.PropertyValue;
import com.tngtech.propertyloader.PropertyLoader;
import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Scope("prototype")
public class BuilderConfiguration {

    private PropertyLoader propertyLoader;
    private Class[] annotationOrder = {CommandLineValue.class, PropertyValue.class, DefaultValue.class};

    public void setPropertyLoader(PropertyLoader propertyLoader) {
        this.propertyLoader = propertyLoader;
    }

    public void setAnnotationOrder(Class[] annotationOrder) {
        this.annotationOrder = annotationOrder;
    }

    public Class[] getAnnotationOrder(){
        return annotationOrder;
    }

    public PropertyLoader getPropertyLoader() {
        return propertyLoader;
    }
}
