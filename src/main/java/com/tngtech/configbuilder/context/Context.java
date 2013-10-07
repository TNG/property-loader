package com.tngtech.configbuilder.context;

import com.tngtech.propertyloader.PropertyLoader;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.*;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

@Configuration
@ComponentScan("com.tngtech.configbuilder")
public class Context {
    private static BeanFactory factory;

    protected Context() {
    }

    @Bean
    @Scope("prototype")
    public StringBuilder stringBuilder() {
        return new StringBuilder();
    }

    @Bean
    public CommandLineParser createCommandLineParser() {
        return new GnuParser();
    }

    @Bean
    @Scope("prototype")
    public Options createOptions() {
        return new Options();
    }

    @Bean
    @Scope("prototype")
    public PropertyLoader createPropertyLoader() {
        return new PropertyLoader();
    }

    @Bean
    public ValidatorFactory getValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }

    public static <T> T getBean(Class<T> clazz) {
        if (factory == null) {
            factory = new AnnotationConfigApplicationContext(Context.class);
        }
        return factory.getBean(clazz);
    }
}
