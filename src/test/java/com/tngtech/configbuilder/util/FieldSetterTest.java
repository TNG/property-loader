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

    private static class TestConfig1 {
        @DefaultValue("user")
        public String testString;
    }
    private static class TestConfig2 {
        @DefaultValue("user")
        public Collection<String> testField;
    }
    private static class TestConfig3 {
        @DefaultValue("user")
        public int testInt;
    }
    private static class TestConfig4 {

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

    }

    @Test
    public void testSetFields() throws Exception {
        when(fieldValueExtractor.extractValue(Matchers.any(Field.class),Matchers.any(BuilderConfiguration.class))).thenReturn("value");
        when(errorMessageSetup.getErrorMessage(Matchers.any(TargetTypeException.class),Matchers.any(String.class),Matchers.any(String.class),Matchers.any(String.class))).thenReturn("TargetTypeException");
        when(annotationHelper.fieldHasAnnotationAnnotatedWith(Matchers.any(Field.class), Matchers.any(Class.class))).thenReturn(true);

        FieldSetter<TestConfig1> fieldSetter = new FieldSetter<>(fieldValueExtractor,errorMessageSetup, annotationHelper);
        TestConfig1 testConfig1 = new TestConfig1();

        fieldSetter.setFields(testConfig1, builderConfiguration);

        assertEquals("value", testConfig1.testString);
    }

    @Test
    public void testSetFieldsThrowsTargetTypeException() throws Exception {
        when(fieldValueExtractor.extractValue(Matchers.any(Field.class),Matchers.any(BuilderConfiguration.class))).thenReturn("value");
        when(errorMessageSetup.getErrorMessage(Matchers.any(TargetTypeException.class),Matchers.any(String.class),Matchers.any(String.class),Matchers.any(String.class))).thenReturn("TargetTypeException");
        when(annotationHelper.fieldHasAnnotationAnnotatedWith(Matchers.any(Field.class), Matchers.any(Class.class))).thenReturn(true);

        FieldSetter<TestConfig2> fieldSetter = new FieldSetter<>(fieldValueExtractor,errorMessageSetup, annotationHelper);
        TestConfig2 testConfig2 = new TestConfig2();

        expectedException.expect(ConfigBuilderException.class);
        expectedException.expectMessage("TargetTypeException");

        fieldSetter.setFields(testConfig2, builderConfiguration);
    }

    @Test
    public void testSetFieldsThrowsIllegalArgumentException() throws Exception {
        when(fieldValueExtractor.extractValue(Matchers.any(Field.class),Matchers.any(BuilderConfiguration.class))).thenReturn(null);
        when(errorMessageSetup.getErrorMessage(Matchers.any(IllegalArgumentException.class),Matchers.any(String.class),Matchers.any(String.class),Matchers.any(String.class))).thenReturn("IllegalArgumentException");
        when(annotationHelper.fieldHasAnnotationAnnotatedWith(Matchers.any(Field.class), Matchers.any(Class.class))).thenReturn(true);

        FieldSetter<TestConfig3> fieldSetter = new FieldSetter<>(fieldValueExtractor,errorMessageSetup, annotationHelper);
        TestConfig3 testConfig3 = new TestConfig3();

        expectedException.expect(ConfigBuilderException.class);
        expectedException.expectMessage("IllegalArgumentException");

        fieldSetter.setFields(testConfig3, builderConfiguration);
    }

    @Test
    public void testSetFieldsForFieldWithoutValueExtractorAnnotation() throws Exception {
        when(fieldValueExtractor.extractValue(Matchers.any(Field.class),Matchers.any(BuilderConfiguration.class))).thenReturn(null);
        when(annotationHelper.fieldHasAnnotationAnnotatedWith(Matchers.any(Field.class), Matchers.any(Class.class))).thenReturn(false);

        FieldSetter<TestConfig4> fieldSetter = new FieldSetter<>(fieldValueExtractor,errorMessageSetup, annotationHelper);
        TestConfig4 testConfig4 = new TestConfig4();

        fieldSetter.setFields(testConfig4, builderConfiguration);

        assertEquals("testString", testConfig4.testString);
    }
}
