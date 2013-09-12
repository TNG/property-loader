package com.tngtech.configbuilder;


import com.tngtech.configbuilder.annotations.DefaultValue;

import java.lang.reflect.Field;

public class ConfigBuilder<T> {

    public T getConfig(Class<T> configClass){

        Field field = configClass.getDeclaredFields()[0];
        DefaultValue value = (DefaultValue) field.getAnnotations()[0];

        T c = null;
        try {
            c = configClass.newInstance();
            field.setAccessible(true);
            field.set(c, value.value());
            return c;
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return c;
    }
}
