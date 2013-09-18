package com.tngtech.propertyloader.context;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.tngtech.propertyloader")
public class Context {
    private static BeanFactory factory;

    protected Context() {
    }

    public static <T> T getBean(Class<T> clazz) {
        if (factory == null) {
            factory = new AnnotationConfigApplicationContext(Context.class);
        }
        return factory.getBean(clazz);
    }
}
