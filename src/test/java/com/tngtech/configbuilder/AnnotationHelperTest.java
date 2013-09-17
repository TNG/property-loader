package com.tngtech.configbuilder;


import com.tngtech.configbuilder.annotations.*;
import com.tngtech.configbuilder.impl.AnnotationHelper;
import com.tngtech.configbuilder.impl.ConfigLoader;
import org.apache.commons.cli.CommandLine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.annotation.Annotation;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnnotationHelperTest {

    private AnnotationHelper annotationHelper;

    @Mock
    private ConfigLoader configLoader;

    @Before
    public void setUp(){
        annotationHelper = new AnnotationHelper(configLoader);
    }

    @Test
    public void testThatLoadPropertiesFromAnnotationsCallsCorrectMethods(){
        Annotation[] annotations = Config.class.getDeclaredAnnotations();

        Properties properties = new Properties();
        properties.put("properties","");
        Properties errors = new Properties();
        errors.put("errors","");
        when(configLoader.loadPropertiesFromAnnotation(Matchers.any(PropertiesFile.class))).thenReturn(properties);
        when(configLoader.loadPropertiesFromAnnotation(Matchers.any(ErrorMessageFile.class))).thenReturn(errors);

        for(Annotation annotation : annotations){
            Properties newProperties = annotationHelper.loadPropertiesFromAnnotations(new Annotation[]{annotation});
            if(annotation.annotationType() == PropertiesFile.class){
                verify(configLoader).loadPropertiesFromAnnotation(Matchers.any(PropertiesFile.class));
                assertEquals(properties,newProperties);
            }
            else if(annotation.annotationType() == ErrorMessageFile.class){
                verify(configLoader).loadPropertiesFromAnnotation(Matchers.any(ErrorMessageFile.class));
                assertEquals(errors,newProperties);
            }
            else{
                assertEquals(new Properties(),newProperties);
            }
        }
    }


    @Test
    public void testThatLoadStringFromAnnotationCallsCorrectMethods(){
        try{
            Annotation[] annotations = Config.class.getDeclaredField("userName").getDeclaredAnnotations();

            CommandLine commandLineArgs = mock(CommandLine.class);
            //commandLineArgs.put("u", "Mueller");

            Properties properties = new Properties();
            //properties.put("user.name","Meier");

            when(configLoader.loadStringFromAnnotation(Matchers.any(DefaultValue.class))).thenReturn("user");
            when(configLoader.loadStringFromAnnotation(Matchers.any(PropertyValue.class), Matchers.any(Properties.class))).thenReturn("Meier");
            when(configLoader.loadStringFromAnnotation(Matchers.any(CommandLineValue.class), Matchers.any(CommandLine.class))).thenReturn("Mueller");

            for(Annotation annotation : annotations){
                String result = annotationHelper.loadStringFromAnnotation(annotation, commandLineArgs, properties);
                if(annotation.annotationType() == DefaultValue.class){
                    verify(configLoader).loadStringFromAnnotation(Matchers.any(DefaultValue.class));
                    assertEquals("user",result);
                }
                else if(annotation.annotationType() == PropertyValue.class){
                    verify(configLoader).loadStringFromAnnotation(Matchers.any(PropertyValue.class), properties);
                    assertEquals("Meier",result);
                }
                else if(annotation.annotationType() == CommandLineValue.class){
                    verify(configLoader).loadStringFromAnnotation(Matchers.any(CommandLineValue.class), commandLineArgs);
                    assertEquals("Mueller",result);
                }
                else{
                    assertEquals(null, result);
                }
            }
        }
        catch(NoSuchFieldException e){}



    }
}




































