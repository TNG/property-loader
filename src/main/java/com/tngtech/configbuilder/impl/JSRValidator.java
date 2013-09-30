package com.tngtech.configbuilder.impl;


import com.tngtech.configbuilder.ConfigBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class JSRValidator<T> {

    private final static Logger log = Logger.getLogger(ConfigBuilder.class);

    public void validate(T instanceOfConfigClass) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(instanceOfConfigClass);
        for(ConstraintViolation constraintViolation : constraintViolations){
            log.warn(constraintViolation.getMessage());
        }
    }
}
