package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotations.CommandLineValue;
import com.tngtech.configbuilder.annotations.ValueProvider;
import com.tngtech.configbuilder.annotations.impl.AnnotationHelper;
import org.apache.commons.cli.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigBuilder<T> {

    private AnnotationHelper annotationHelper;

    private Class<T> configClass;
    private Annotation[] annotations;
    private Method[] methods;
    private Properties properties = new Properties();
    private LinkedHashMap<Field,String> fields;
    private CommandLine commandLineArgs;

    public ConfigBuilder(AnnotationHelper annotationHelper) {
        this.annotationHelper = annotationHelper;
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
    public CommandLine getCommandLineArgs(){
        return this.commandLineArgs;
    }


   public T build(){

        T config = null;
        try
        {
            for(Map.Entry<Field,String> fieldEntry: fields.entrySet()){
                Field field = fieldEntry.getKey();
                Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
                fieldEntry.setValue(annotationHelper.loadStringFromAnnotations(fieldAnnotations, commandLineArgs, properties));
            }
            config = configClass.newInstance();

            for(Map.Entry<Field,String> fieldEntry: fields.entrySet()){
                Field field = fieldEntry.getKey();
                field.setAccessible(true);
                if(field.isAnnotationPresent(ValueProvider.class)){

                }
                else if(field.getType() == String.class){
                    field.set(config, fieldEntry.getValue());
                }
                else{
                    //exception
                }
                //if(field.isAnnotationPresent(JS303.class)){ ... }


            }
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
            fields.put(field,null);
        }
        this.methods = configClass.getDeclaredMethods();
        this.properties = annotationHelper.loadPropertiesFromAnnotations(annotations);

        return this;
    }

    public ConfigBuilder<T> withCommandLineArgs(String[] args){

        Options options = new Options();
        for(Field field : fields.keySet()){
            if(field.isAnnotationPresent(CommandLineValue.class)){
                CommandLineValue commandLineValue = field.getAnnotation(CommandLineValue.class);
                options.addOption(commandLineValue.value(), true, commandLineValue.description());
            }
        }

        CommandLineParser parser = new GnuParser();
        try {
            this.commandLineArgs = parser.parse(options, args);
        } catch (ParseException e) {}
        return this;
    }


}
