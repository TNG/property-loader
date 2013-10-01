package com.tngtech.configbuilder.impl;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnnotationUtilsTest {


    @Mock
    private Annotation annotation1;
    @Mock
    private Annotation annotation2;
    @Mock
    private MiscFactory miscFactory;
    @Mock
    private ArrayList<Object> annotationList;

    private Annotation[] annotations = {annotation1,annotation2};


    private AnnotationUtils annotationUtils;

    @Before
    public void setUp() throws Exception {
        annotationUtils = new AnnotationUtils();
    }

    @Test
    public void testGetAnnotationsOfTypeFromField() throws Exception {
    }

    @Test
    public void testGetAnnotationsOfTypeFromClass() throws Exception {

    }

    @Test
    public void testGetAnnotationsInOrder() throws Exception {

    }

    @Test
    public void testGetFieldsAnnotatedWith() throws Exception {

    }
}
