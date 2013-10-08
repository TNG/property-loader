package com.tngtech.configbuilder.exception;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ValidatorException extends RuntimeException {

    Set<ConstraintViolation> constraintViolations;

    public <T>ValidatorException(String message, Set<ConstraintViolation<T>> constraintViolations) {
        super(message);
        this.constraintViolations = Sets.newHashSet(Iterables.transform(constraintViolations, new Function<ConstraintViolation<T>, ConstraintViolation>() {
            @Override
            public ConstraintViolation apply(ConstraintViolation<T> constraintViolation) {
                return constraintViolation;
            }
        }));

    }
}
