package com.tngtech.configbuilder.util;


import com.tngtech.configbuilder.annotation.validation.Validation;
import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.configbuilder.exception.ConfigBuilderException;
import com.tngtech.configbuilder.exception.ValidatorException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Method;
import java.util.Set;

@Component
public class ConfigValidator<T> {

    private final static Logger log = Logger.getLogger(ConfigValidator.class);

    private final BeanFactory beanFactory;
    private final ErrorMessageSetup errorMessageSetup;
    private final AnnotationHelper annotationHelper;

    @Autowired
    public ConfigValidator(BeanFactory miscFactory, ErrorMessageSetup errorMessageSetup, AnnotationHelper annotationHelper) {
        this.beanFactory = miscFactory;
        this.errorMessageSetup = errorMessageSetup;
        this.annotationHelper = annotationHelper;
    }

    public void validate(T instanceOfConfigClass) {
        log.info(String.format("validating instance of %s", instanceOfConfigClass.getClass()));
        callValidationMethods(instanceOfConfigClass);
        callJSRValidation(instanceOfConfigClass);
    }

    private void callValidationMethods(T instanceOfConfigClass) {
        for(Method method : annotationHelper.getFMethodsAnnotatedWith(instanceOfConfigClass.getClass(), Validation.class)) {
            try{
                method.invoke(instanceOfConfigClass);
            }
            catch (Exception e) {
                throw new ConfigBuilderException(errorMessageSetup.getErrorMessage(e),e);
            }
        }
    }

    private void callJSRValidation(T instanceOfConfigClass) {
        ValidatorFactory factory = beanFactory.getBean(ValidatorFactory.class);
        javax.validation.Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(instanceOfConfigClass);
        if(!constraintViolations.isEmpty()){
            throw new ValidatorException(errorMessageSetup.getErrorMessage(ValidatorException.class),constraintViolations);
        }
    }
}
