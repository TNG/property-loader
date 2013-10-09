package com.tngtech.configbuilder.util;

import com.tngtech.configbuilder.annotation.valueextractor.DefaultValue;
import com.tngtech.configbuilder.configuration.BuilderConfiguration;
import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.configbuilder.exception.ConfigBuilderException;
import com.tngtech.configbuilder.exception.TargetTypeException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FieldSetterTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static class TestConfig {
        @DefaultValue("user")
        public String testString;
    }
    private static class TestConfigForTargetTypeException {
        @DefaultValue("user")
        public Collection<String> testField;
    }
    private static class TestConfigForIllegalArgumentException {
        @DefaultValue("user")
        public int testInt;
    }
    private static class TestConfigWithoutAnnotations {

        public String testString ="testString";
    }


    @Mock
    private BuilderConfiguration builderConfiguration;
    @Mock
    private FieldValueExtractor fieldValueExtractor;
    @Mock
    private ErrorMessageSetup errorMessageSetup;
    @Mock
    private AnnotationHelper annotationHelper;


    @Before
    public void setUp() throws Exception {
        when(annotationHelper.fieldHasAnnotationAnnotatedWith(Matchers.any(Field.class), Matchers.any(Class.class))).thenReturn(true);
    }

    @Test
    public void testSetFields() throws Exception {
        when(fieldValueExtractor.extractValue(Matchers.any(Field.class),Matchers.any(BuilderConfiguration.class))).thenReturn("value");
        when(errorMessageSetup.getErrorMessage(Matchers.any(TargetTypeException.class),Matchers.any(String.class),Matchers.any(String.class),Matchers.any(String.class))).thenReturn("TargetTypeException");

        FieldSetter<TestConfig> fieldSetter = new FieldSetter<>(fieldValueExtractor,errorMessageSetup, annotationHelper);
        TestConfig testConfig = new TestConfig();

        fieldSetter.setFields(testConfig, builderConfiguration);

        assertEquals("value", testConfig.testString);
    }

    @Test
    public void testSetFieldsThrowsTargetTypeException() throws Exception {
        when(fieldValueExtractor.extractValue(Matchers.any(Field.class),Matchers.any(BuilderConfiguration.class))).thenReturn("value");
        when(errorMessageSetup.getErrorMessage(Matchers.any(TargetTypeException.class),Matchers.any(String.class),Matchers.any(String.class),Matchers.any(String.class))).thenReturn("TargetTypeException");

        FieldSetter<TestConfigForTargetTypeException> fieldSetter = new FieldSetter<>(fieldValueExtractor,errorMessageSetup, annotationHelper);
        TestConfigForTargetTypeException testConfigForTargetTypeException = new TestConfigForTargetTypeException();

        expectedException.expect(ConfigBuilderException.class);
        expectedException.expectMessage("TargetTypeException");

        fieldSetter.setFields(testConfigForTargetTypeException, builderConfiguration);
    }

    @Test
    public void testSetFieldsThrowsIllegalArgumentException() throws Exception {
        when(fieldValueExtractor.extractValue(Matchers.any(Field.class),Matchers.any(BuilderConfiguration.class))).thenReturn(null);
        when(errorMessageSetup.getErrorMessage(Matchers.any(IllegalArgumentException.class),Matchers.any(String.class),Matchers.any(String.class),Matchers.any(String.class))).thenReturn("IllegalArgumentException");

        FieldSetter<TestConfigForIllegalArgumentException> fieldSetter = new FieldSetter<>(fieldValueExtractor,errorMessageSetup, annotationHelper);
        TestConfigForIllegalArgumentException testConfigForIllegalArgumentException = new TestConfigForIllegalArgumentException();

        expectedException.expect(ConfigBuilderException.class);
        expectedException.expectMessage("IllegalArgumentException");

        fieldSetter.setFields(testConfigForIllegalArgumentException, builderConfiguration);
    }

    @Test
    public void testSetFieldsForFieldWithoutValueExtractorAnnotation() throws Exception {
        when(fieldValueExtractor.extractValue(Matchers.any(Field.class),Matchers.any(BuilderConfiguration.class))).thenReturn(null);
        when(annotationHelper.fieldHasAnnotationAnnotatedWith(Matchers.any(Field.class), Matchers.any(Class.class))).thenReturn(false);

        FieldSetter<TestConfigWithoutAnnotations> fieldSetter = new FieldSetter<>(fieldValueExtractor,errorMessageSetup, annotationHelper);
        TestConfigWithoutAnnotations testConfigWithoutAnnotations = new TestConfigWithoutAnnotations();

        fieldSetter.setFields(testConfigWithoutAnnotations, builderConfiguration);

        assertEquals("testString", testConfigWithoutAnnotations.testString);
    }
}
