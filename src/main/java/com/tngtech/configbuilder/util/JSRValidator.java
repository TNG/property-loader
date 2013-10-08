package com.tngtech.configbuilder.util;


import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.configbuilder.exception.ValidatorException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class JSRValidator<T> {

    private final static Logger log = Logger.getLogger(JSRValidator.class);

    private final BeanFactory beanFactory;
    private final ErrorMessageSetup errorMessageSetup;

    @Autowired
    public JSRValidator(BeanFactory miscFactory, ErrorMessageSetup errorMessageSetup) {
        this.beanFactory = miscFactory;
        this.errorMessageSetup = errorMessageSetup;
    }

    public void validate(T instanceOfConfigClass) {
        log.info(String.format("validating instance of %s", instanceOfConfigClass.getClass()));
        ValidatorFactory factory = beanFactory.getBean(ValidatorFactory.class);
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(instanceOfConfigClass);
        if(!constraintViolations.isEmpty()){
            throwConstraintViolationException(constraintViolations);
        }
    }

    private void throwConstraintViolationException(Set<ConstraintViolation<T>> constraintViolations) {
        StringBuilder sb = new StringBuilder();
        for(ConstraintViolation constraintViolation : constraintViolations){
            sb.append("\n");
            sb.append(errorMessageSetup.getErrorMessage(constraintViolation.getMessage()));
        }
        throw new ValidatorException(errorMessageSetup.getErrorMessage(ValidatorException.class) + sb);
    }
}
