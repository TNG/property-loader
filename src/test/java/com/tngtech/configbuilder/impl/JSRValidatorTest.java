package com.tngtech.configbuilder.impl;

import com.google.common.collect.Sets;
import com.tngtech.configbuilder.ConfigBuilderException;
import com.tngtech.configbuilder.TestConfig;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JSRValidatorTest {

    private JSRValidator<TestConfig> jsrValidator;

    @Mock
    private MiscFactory miscFactory;
    @Mock
    private ValidatorFactory validatorFactory;
    @Mock
    private Validator validator;
    @Mock
    private TestConfig testConfig;
    @Mock
    private ConstraintViolation constraintViolation1;
    @Mock
    private ConstraintViolation constraintViolation2;
    @Mock
    private ErrorMessageSetup errorMessageSetup;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {

        jsrValidator = new JSRValidator<>(miscFactory, errorMessageSetup);

        Set<ConstraintViolation<TestConfig>> constraintViolations = Sets.newHashSet();
        constraintViolations.add(constraintViolation1);
        constraintViolations.add(constraintViolation2);

        when(miscFactory.getValidatorFactory()).thenReturn(validatorFactory);
        when(miscFactory.createStringBuilder()).thenReturn(new StringBuilder());
        when(validatorFactory.getValidator()).thenReturn(validator);
        when(validator.validate(testConfig)).thenReturn(constraintViolations);
        when(constraintViolation1.getMessage()).thenReturn("ConstraintViolation1");
        when(constraintViolation2.getMessage()).thenReturn("ConstraintViolation2");
        when(errorMessageSetup.getString("ConstraintViolation1")).thenReturn("Constraint Violation 1");
        when(errorMessageSetup.getString("ConstraintViolation2")).thenReturn("Constraint Violation 2");
    }

    @Test
    public void testValidate() throws Exception {
        try{
            jsrValidator.validate(testConfig);
            verify(constraintViolation1).getMessage();
            verify(constraintViolation2).getMessage();
            fail("expected ConfigBuilderException");
        }
        catch(ConfigBuilderException e) {
            assertTrue(e.toString().contains("Constraint Violation 1"));
            assertTrue(e.toString().contains("Constraint Violation 2"));
        }
    }
}
