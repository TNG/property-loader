package com.tngtech.configbuilder;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.annotations.*;
import com.tngtech.configbuilder.impl.AnnotationHandler;
import com.tngtech.configbuilder.impl.AnnotationHelper;
import com.tngtech.configbuilder.impl.FieldValueProvider;
import org.apache.commons.cli.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
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

        T instanceOfConfigClass = null;
        try
        {
            instanceOfConfigClass = configClass.newInstance();
            instanceOfConfigClass = setFields(instanceOfConfigClass);
            return instanceOfConfigClass;
        }
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        return instanceOfConfigClass;
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

            String fieldConfiguringString = getFieldConfiguringString(field, commandLineArgs, properties);
            field.setAccessible(true);
            if(field.isAnnotationPresent(ValueProvider.class) && fieldConfiguringString != null){
                ValueProvider valueProvider = field.getAnnotation(ValueProvider.class);
                Object fieldValue = getFieldValue(valueProvider.value(), instanceOfConfigClass, fieldConfiguringString);
                field.set(instanceOfConfigClass, fieldValue);
            }
            else if(field.getType() == String.class){
                field.set(instanceOfConfigClass, fieldConfiguringString);
            }
            else{
                //exception
            }
        }
        return instanceOfConfigClass;
    }

    private String getFieldConfiguringString(Field field, CommandLine commandLineArgs, Properties properties) throws InstantiationException, IllegalAccessException{

        List<Class> fieldAnnotationOrder = Lists.newArrayList(annotationOrder);
        String fieldConfiguringString = null;
        if(field.isAnnotationPresent(LoadingOrder.class)){
            fieldAnnotationOrder = Lists.newArrayList(field.getAnnotation(LoadingOrder.class).value());
        }
        for(Class clazz : fieldAnnotationOrder){
            if(field.isAnnotationPresent(clazz)){
                Annotation annotation = field.getAnnotation(clazz);
                StringFindingAnnotationHandler stringFindingAnnotationHandler = annotation.annotationType().getAnnotation(StringFindingAnnotationHandler.class);
                Class annotationHandlerClass = stringFindingAnnotationHandler.value();
                AnnotationHandler instanceOfAnnotationHandler = (AnnotationHandler)annotationHandlerClass.newInstance();
                instanceOfAnnotationHandler.setProperties(properties);
                instanceOfAnnotationHandler.setCommandLineArgs(commandLineArgs);
                fieldConfiguringString = instanceOfAnnotationHandler.getString(annotation);

                //value = annotationHelper.loadStringFromAnnotation(ann, commandLineArgs, properties);

                if(fieldConfiguringString != null){
                    break;
                }
            }
        }
        return fieldConfiguringString;
    }

    private Object getFieldValue(Class innerClass, Object instanceOfOuterClass, String fieldString){
        Object fieldValue = null;

        try{
            //T falsch
            /*Constructor<T> tConstructor = innerClass.getConstructor(instanceOfOuterClass.getClass());
            T instanceOfInnerClass = tConstructor.newInstance(instanceOfOuterClass);
            FieldValueProvider cP = (FieldValueProvider)instanceOfInnerClass;*/
            Method method = innerClass.getMethod("getValue", String.class);
            fieldValue = method.invoke(null,fieldString);
            return fieldValue;
        }
        catch (InvocationTargetException e) {}
        catch (NoSuchMethodException e) {}
        catch (IllegalAccessException e) {}
        return fieldValue;
    }
}
