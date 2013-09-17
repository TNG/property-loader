package com.tngtech.configbuilder;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.annotations.*;
import com.tngtech.configbuilder.impl.AnnotationHandler;
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
    private List<Field> fields;
    private CommandLine commandLineArgs;
    private Class[] annotationOrder = {CommandLineValue.class, PropertyValue.class, DefaultValue.class};

    public ConfigBuilder(AnnotationHelper annotationHelper) {
        this.annotationHelper = annotationHelper;
    }

    public Class<T> getConfigClass(){
        return this.configClass;
    }

    public List<Field> getFields(){
        return this.fields;
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
            config = configClass.newInstance();
            config = setFields(config);
            return config;
        }
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        return config;
    }

    public ConfigBuilder<T> forClass(Class<T> configClass){

        this.configClass = configClass;
        this.fields = Lists.newArrayList(configClass.getDeclaredFields());
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
        for(Field field : fields){
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

    private T setFields(T instanceOfConfigClass) throws InstantiationException, IllegalAccessException{

        for(Field field: fields){

            String fieldString = getFieldString(field, commandLineArgs, properties);
            field.setAccessible(true);
            if(field.isAnnotationPresent(ValueProvider.class) && fieldString != null){
                ValueProvider vP = field.getAnnotation(ValueProvider.class);

                Object obj = getFieldValue(vP.value(), instanceOfConfigClass, fieldString);
                field.set(instanceOfConfigClass, obj);
            }
            else if(field.getType() == String.class){
                field.set(instanceOfConfigClass, fieldString);
            }
            else{
                //exception
            }
        }
        return instanceOfConfigClass;
    }

    private String getFieldString(Field field, CommandLine commandLineArgs, Properties properties) throws InstantiationException, IllegalAccessException{

        List<Class> fieldAnnotationOrder = Lists.newArrayList(annotationOrder);
        String value = null;
        if(field.isAnnotationPresent(LoadingOrder.class)){
            fieldAnnotationOrder = Lists.newArrayList(field.getAnnotation(LoadingOrder.class).value());
        }
        for(Class clazz : fieldAnnotationOrder){
            if(field.isAnnotationPresent(clazz)){
                Annotation ann = field.getAnnotation(clazz);

                StringFindingAnnotationHandler stringFindingAnnotationHandler = ann.annotationType().getAnnotation(StringFindingAnnotationHandler.class);
                Class annotationHandler = stringFindingAnnotationHandler.value();
                AnnotationHandler instanceOfAnnotationHandler = (AnnotationHandler)annotationHandler.newInstance();
                instanceOfAnnotationHandler.setProperties(properties);
                instanceOfAnnotationHandler.setCommandLineArgs(commandLineArgs);
                value = instanceOfAnnotationHandler.getString(ann);

                //value = annotationHelper.loadStringFromAnnotation(ann, commandLineArgs, properties);

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
