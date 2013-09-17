package com.tngtech.configbuilder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tngtech.configbuilder.annotations.*;
import com.tngtech.configbuilder.impl.AnnotationHelper;
import com.tngtech.configbuilder.impl.FieldValueProvider;
import org.apache.commons.cli.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ConfigBuilder<T> {

    private AnnotationHelper annotationHelper;

    private Class<T> configClass;
    private Properties properties = new Properties();
    private Properties errors = new Properties();
    private LinkedHashMap<Field,String> fields;
    private CommandLine commandLineArgs;
    private Class[] annotationOrder = {CommandLineValue.class, PropertyValue.class, DefaultValue.class};

    public ConfigBuilder(AnnotationHelper annotationHelper) {
        this.annotationHelper = annotationHelper;
    }

    public Class<T> getConfigClass(){
        return this.configClass;
    }

    public LinkedHashMap<Field,String> getFields(){
        return this.fields;
    }


    public Properties getProperties(){
        return this.properties;
    }
    public Properties getErrorMessages() {
        return errors;
    }
    public CommandLine getCommandLineArgs(){
        return this.commandLineArgs;
    }


   public T build(){

        T config = null;
        try
        {
            config = configClass.newInstance();

            for(Map.Entry<Field,String> fieldEntry: fields.entrySet()){

                Field field = fieldEntry.getKey();
                String fieldString = getFieldString(field, commandLineArgs, properties);
                field.setAccessible(true);
                if(field.isAnnotationPresent(ValueProvider.class) && fieldString != null){
                    ValueProvider vP = field.getAnnotation(ValueProvider.class);

                    Object obj = getFieldValue(vP.value(), config, fieldString);
                    field.set(config, obj);
                }
                else if(field.getType() == String.class){
                    field.set(config, fieldString);
                }
                else{
                    //exception
                }

            }
            return config;
        }

        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        return config;
    }

    public ConfigBuilder<T> forClass(Class<T> configClass){

        this.configClass = configClass;
        this.fields = Maps.newLinkedHashMap();
        for(Field field : configClass.getDeclaredFields()){
            fields.put(field,null);
        }
        if(configClass.isAnnotationPresent(PropertiesFile.class)){
            this.properties = annotationHelper.loadPropertiesFromAnnotation(configClass.getAnnotation(PropertiesFile.class));
        }
        if(configClass.isAnnotationPresent(ErrorMessageFile.class)){
            this.errors = annotationHelper.loadPropertiesFromAnnotation(configClass.getAnnotation(ErrorMessageFile.class));
        }
        if(configClass.isAnnotationPresent(LoadingOrder.class)){
            this.annotationOrder = configClass.getAnnotation(LoadingOrder.class).value();
        }


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

    private String getFieldString(Field field, CommandLine commandLineArgs, Properties properties){

        List<Class> fieldAnnotationOrder = Lists.newArrayList(annotationOrder);
        String value = null;
        if(field.isAnnotationPresent(LoadingOrder.class)){
            fieldAnnotationOrder = Lists.newArrayList(field.getAnnotation(LoadingOrder.class).value());
        }
        for(Class clazz : fieldAnnotationOrder){
            if(field.isAnnotationPresent(clazz)){
                Annotation ann = field.getAnnotation(clazz);
                value = annotationHelper.loadStringFromAnnotation(ann, commandLineArgs, properties);
                if(value != null){
                    break;
                }
            }
        }
        return value;
    }

    private Object getFieldValue(Class innerClass, Object instanceOfOuterClass, String fieldString){
        Object obj = null;

        try{
            Constructor<T> tConstructor = innerClass.getConstructor(instanceOfOuterClass.getClass());
            T instanceOfInnerClass = tConstructor.newInstance(instanceOfOuterClass);
            FieldValueProvider cP = (FieldValueProvider)instanceOfInnerClass;
            obj = cP.getValue(fieldString);
            return obj;
        }
        catch (InvocationTargetException e) {}
        catch (NoSuchMethodException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        return obj;
    }

}
