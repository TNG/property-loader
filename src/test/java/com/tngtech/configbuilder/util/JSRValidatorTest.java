package com.tngtech.configbuilder.util;

import com.google.common.collect.Sets;
import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.configbuilder.testclasses.TestConfig;
import com.tngtech.configbuilder.exception.ValidatorException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.BeanFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JSRValidatorTest {

    private JSRValidator<TestConfig> jsrValidator;

    @Mock
    private BeanFactory beanFactory;
    @Mock
    private ValidatorFactory validatorFactory;
    @Mock
    private Validator validator;
    @Mock
    private TestConfig testConfig;
    @Mock
    private ConstraintViolation<TestConfig> constraintViolation1, constraintViolation2;
    @Mock
    private ErrorMessageSetup errorMessageSetup;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {

        jsrValidator = new JSRValidator<>(beanFactory, errorMessageSetup);
        when(beanFactory.getBean(ValidatorFactory.class)).thenReturn(validatorFactory);
        when(beanFactory.getBean(StringBuilder.class)).thenReturn(new StringBuilder());
        when(validatorFactory.getValidator()).thenReturn(validator);


    }

    @Test
    public void testValidateWithConstraintViolations() throws Exception {

        Set<ConstraintViolation<TestConfig>> constraintViolations = Sets.newHashSet(constraintViolation1, constraintViolation2);

        when(validator.validate(testConfig)).thenReturn(constraintViolations);
        when(constraintViolation1.getMessage()).thenReturn("ConstraintViolation1");
        when(constraintViolation2.getMessage()).thenReturn("ConstraintViolation2");
        when(errorMessageSetup.getErrorMessage("ConstraintViolation1")).thenReturn("Constraint Violation 1");
        when(errorMessageSetup.getErrorMessage("ConstraintViolation2")).thenReturn("Constraint Violation 2");
        when(errorMessageSetup.getErrorMessage(Matchers.any(ValidatorException.class))).thenReturn("Validation found the following constraint violations:");

        try{
            jsrValidator.validate(testConfig);
            verify(constraintViolation1).getMessage();
            verify(constraintViolation2).getMessage();
            fail("expected ConfigBuilderException");
        }
        catch(ValidatorException e) {
            System.out.println(e.toString());
            assertTrue(e.toString().contains("Constraint Violation 1"));
            assertTrue(e.toString().contains("Constraint Violation 2"));
        }
    }

    @Test
    public void testValidateWithoutConstraintViolations() throws Exception {

        Set<ConstraintViolation<TestConfig>> constraintViolations = Sets.newHashSet();
        when(validator.validate(testConfig)).thenReturn(constraintViolations);

        jsrValidator.validate(testConfig);
    }
}
