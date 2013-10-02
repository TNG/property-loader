package com.tngtech.configbuilder.impl;


import com.tngtech.configbuilder.ConfigBuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ResourceBundle;
import java.util.Set;

@Component
public class JSRValidator<T> {

    private final MiscFactory miscFactory;
    private final ErrorMessageSetup errorMessageSetup;

    @Autowired
    public JSRValidator(MiscFactory miscFactory, ErrorMessageSetup errorMessageSetup) {
        this.miscFactory = miscFactory;
        this.errorMessageSetup = errorMessageSetup;
    }

    public void validate(T instanceOfConfigClass) {
        ValidatorFactory factory = miscFactory.getValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(instanceOfConfigClass);
        if(!constraintViolations.isEmpty()){
            StringBuilder sb = miscFactory.createStringBuilder();
            sb.append(errorMessageSetup.getString("constraintViolationsFound"));
            for(ConstraintViolation constraintViolation : constraintViolations){
                sb.append("\n" + errorMessageSetup.getString(constraintViolation.getMessage()));
            }
            throw new ConfigBuilderException(sb.toString());
        }
    }
}
