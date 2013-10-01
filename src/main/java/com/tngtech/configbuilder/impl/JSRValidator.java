package com.tngtech.configbuilder.impl;


import com.tngtech.configbuilder.ConfigBuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class JSRValidator<T> {

    private final MiscFactory miscFactory;

    @Autowired
    public JSRValidator(MiscFactory miscFactory) {
        this.miscFactory = miscFactory;
    }

    public void validate(T instanceOfConfigClass) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(instanceOfConfigClass);
        if(!constraintViolations.isEmpty()){
            StringBuilder sb = miscFactory.getStringBuilder();
            for(ConstraintViolation constraintViolation : constraintViolations){
                sb.append(constraintViolation.toString() + "\n");
            }
            throw new ConfigBuilderException(sb.toString());
        }
    }
}
