package com.tngtech.configbuilder.impl;

import com.tngtech.configbuilder.ConfigBuilderException;
import com.tngtech.configbuilder.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FieldSetterTest {


    private TestConfig testConfig = new TestConfig();
    @Mock
    private BuilderConfiguration builderConfiguration;
    @Mock
    private FieldValueExtractor fieldValueExtractor;
    @Mock
    private ErrorMessageSetup errorMessageSetup;

    private FieldSetter<TestConfig> fieldSetter;

    @Before
    public void setUp() throws Exception {
        fieldSetter = new FieldSetter<>(fieldValueExtractor,errorMessageSetup);
        when(fieldValueExtractor.extractValue(Matchers.any(Field.class),Matchers.any(BuilderConfiguration.class))).thenReturn("value");
        when(errorMessageSetup.getString("targetTypeException")).thenReturn("cannot set field fieldName of type fieldType to object of type valueType");
    }

    @Test
    public void testSetFields() throws Exception {
        try{
            fieldSetter.setFields(testConfig,builderConfiguration);
            fail("should throw exception");
        }
        catch(ConfigBuilderException e) {
            assertEquals(e.getMessage(),"cannot set field pidFixes of type java.util.Collection to object of type java.lang.String");
            assertEquals(testConfig.getHelloWorld(), "value");
            assertEquals(testConfig.getSurName(), "value");
            assertEquals(testConfig.getUserName(), "value");
        }
    }
}
