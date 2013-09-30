package com.tngtech.configbuilder.impl;


import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

@Component
public class AnnotationUtils {
    public List<Annotation> getAnnotationsOfType(Field field, Class annotationClass){
        List<Annotation> result = Lists.newArrayList();
        for(Annotation annotation : field.getDeclaredAnnotations()){
            if(annotation.annotationType().isAnnotationPresent(annotationClass)){
                result.add(annotation);
            }
        }
        return result;
    }

    public List<Annotation> getAnnotationsOfType(Class clazz, Class annotationClass){
        List<Annotation> result = Lists.newArrayList();
        for(Annotation annotation : clazz.getDeclaredAnnotations()){
            if(annotation.annotationType().isAnnotationPresent(annotationClass)){
                result.add(annotation);
            }
        }
        return result;
    }

    public List<Annotation> getAnnotationsInOrder(Field field, Class[] annotationOrder){
        List<Annotation> result = Lists.newArrayList();
        for(Class annotationClass : annotationOrder){
            if(field.isAnnotationPresent(annotationClass)){
                result.add(field.getAnnotation(annotationClass));
            }
        }
        return result;
    }

    public Set<Field> getFieldsAnnotatedWith(Class clazz, Class annotationClass){
        return getAllFields(clazz, withAnnotation(annotationClass));
    }
}
