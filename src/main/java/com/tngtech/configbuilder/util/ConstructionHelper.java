package com.tngtech.configbuilder.util;

import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.configbuilder.exception.ConfigBuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

@Component
public class ConstructionHelper<T> {

    private ErrorMessageSetup errorMessageSetup;

    @Autowired
    public ConstructionHelper(ErrorMessageSetup errorMessageSetup){
        this.errorMessageSetup = errorMessageSetup;
    }

    @SuppressWarnings("unchecked")
    public Constructor<T> findSuitableConstructor(Class<T> configClass, Object... objects) {
        Constructor[] constructors = configClass.getDeclaredConstructors();
        for (Constructor<T> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length != objects.length) break;
            boolean isConstructor = true;
            for (int i = 0; i < parameterTypes.length; i++) {
                isConstructor &= parameterTypes[i].isAssignableFrom(objects[i].getClass());
            }
            if (isConstructor) {
                return constructor;
            }

        }
        throw new ConfigBuilderException(errorMessageSetup.getErrorMessage("noSuitableConstructorFound"));
    }
}
