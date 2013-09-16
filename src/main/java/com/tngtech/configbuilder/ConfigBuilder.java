package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotations.impl.ConfigLoader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Properties;

public class ConfigBuilder<T> {

    private ConfigLoader configLoader;

    private Class<T> configClass;
    private Annotation[] annotations;
    private Method[] methods;
    private Properties properties;
    private LinkedHashMap<Field,String> fields;

    public ConfigBuilder(ConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    public Class<T> getConfigClass(){
        return this.configClass;
    }

    public LinkedHashMap<Field,String> getFields(){
        return this.fields;
    }

    public Annotation[] getAnnotations(){
        return this.annotations;
    }
    public Properties getProperties(){
        return this.properties;
    }


   public T build(){

        T config = null;
        try
        {
            config = configClass.newInstance();
            return config;
        }
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        return config;
    }

    public ConfigBuilder<T> forClass(Class<T> configClass){

        this.configClass = configClass;
        this.annotations = configClass.getDeclaredAnnotations();
        this.fields = new LinkedHashMap<>();
        for(Field field : configClass.getDeclaredFields()){
            fields.put(field,"");
        }
        this.methods = configClass.getDeclaredMethods();
        //this.properties = configLoader.loadPropertiesFromAnnotations(annotations);
        return this;
    }


}
