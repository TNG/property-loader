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
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {

        jsrValidator = new JSRValidator<>(beanFactory, errorMessageSetup);
        when(beanFactory.getBean(ValidatorFactory.class)).thenReturn(validatorFactory);
        when(validatorFactory.getValidator()).thenReturn(validator);
    }

    @Test
    public void testValidateWithConstraintViolations() throws Exception {

        Set<ConstraintViolation<TestConfig>> constraintViolations = Sets.newHashSet(constraintViolation1, constraintViolation2);

        when(validator.validate(testConfig)).thenReturn(constraintViolations);
        when(errorMessageSetup.getErrorMessage(Matchers.any(Class.class))).thenReturn("Validation found the following constraint violations:");

        expectedException.expect(ValidatorException.class);
        expectedException.expectMessage("Validation found the following constraint violations:");

        jsrValidator.validate(testConfig);
    }

    @Test
    public void testValidateWithoutConstraintViolations() throws Exception {

        Set<ConstraintViolation<TestConfig>> constraintViolations = Sets.newHashSet();
        when(validator.validate(testConfig)).thenReturn(constraintViolations);

        jsrValidator.validate(testConfig);
    }
}
