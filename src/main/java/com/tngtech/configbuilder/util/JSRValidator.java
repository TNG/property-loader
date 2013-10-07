package com.tngtech.configbuilder.util;


import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.configbuilder.exception.ValidatorException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class JSRValidator<T> {

    private final BeanFactory beanFactory;
    private final ErrorMessageSetup errorMessageSetup;

    @Autowired
    public JSRValidator(BeanFactory miscFactory, ErrorMessageSetup errorMessageSetup) {
        this.beanFactory = miscFactory;
        this.errorMessageSetup = errorMessageSetup;
    }

    public void validate(T instanceOfConfigClass) {
        ValidatorFactory factory = beanFactory.getBean(ValidatorFactory.class);
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(instanceOfConfigClass);
        if(!constraintViolations.isEmpty()){
            throwConstraintViolationException(constraintViolations);
        }
    }

    private void throwConstraintViolationException(Set<ConstraintViolation<T>> constraintViolations) {
        StringBuilder sb = beanFactory.getBean(StringBuilder.class);
        for(ConstraintViolation constraintViolation : constraintViolations){
            sb.append("\n");
            sb.append(errorMessageSetup.getErrorMessage(constraintViolation.getMessage()));
        }
        ValidatorException validatorException = new ValidatorException();
        String message = errorMessageSetup.getErrorMessage(validatorException);
        throw new ValidatorException(message + sb);
    }
}
