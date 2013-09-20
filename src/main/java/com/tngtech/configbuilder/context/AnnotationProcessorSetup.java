package com.tngtech.configbuilder.context;

import com.google.common.collect.ImmutableMap;
import com.tngtech.configbuilder.annotationhandlers.AnnotationProcessor;
import com.tngtech.configbuilder.annotationhandlers.AnnotationPropertyLoaderConfiguration;
import com.tngtech.configbuilder.annotationhandlers.AnnotationValueExtractor;
import com.tngtech.configbuilder.annotations.config.AnnotationValidator;
import com.tngtech.configbuilder.annotations.config.PropertyLoaderConfigurator;
import com.tngtech.configbuilder.annotations.config.ValueExtractor;
import com.tngtech.configbuilder.validators.annotation.AnnotationValidatorAbstract;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

@Component
public class AnnotationProcessorSetup implements ApplicationListener<ContextRefreshedEvent> {

    private final ApplicationContext context;

    @Autowired
    public AnnotationProcessorSetup(ApplicationContext context)
    {
        this.context = context;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        AnnotationProcessor annotationProcessor = context.getBean(AnnotationProcessor.class);

        annotationProcessor.addToPropertyConfiguratorMap(getMapFor(PropertyLoaderConfigurator.class, AnnotationPropertyLoaderConfiguration.class));
        annotationProcessor.addToAnnotationValidatorMap(getMapFor(AnnotationValidator.class, AnnotationValidatorAbstract.class));
        annotationProcessor.addToValueProvidingAnnotationMap(getMapFor(ValueExtractor.class, AnnotationValueExtractor.class));
    }

    private <K extends Annotation, V> Map<Class<? extends K>, ? extends V> getMapFor(Class<? extends Annotation> annotationClass, Class<V> objectClass)
    {
        Reflections reflections = new Reflections("com.tngtech.configbuilder");

        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(annotationClass);

        ImmutableMap.Builder<Class<? extends K>, V> transformerMap = ImmutableMap.builder();
        for (Class<?> annotatedClass: annotatedClasses)
        {
            Class<V> value;
            Annotation annotation;

            try {
                annotation = annotatedClass.getAnnotation(annotationClass);
                //noinspection unchecked
                value = (Class<V>)annotationClass.getMethod("value").invoke(annotation);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            //noinspection unchecked
            transformerMap.put((Class<K>)annotatedClass, context.getBean(value));
        }

        return transformerMap.build();
    }
}
