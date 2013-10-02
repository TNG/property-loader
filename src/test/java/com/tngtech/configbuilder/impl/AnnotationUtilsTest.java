package com.tngtech.configbuilder.impl;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.TestConfig;
import com.tngtech.configbuilder.annotations.CommandLineValue;
import com.tngtech.configbuilder.annotations.LoadingOrder;
import com.tngtech.configbuilder.annotations.PropertyValue;
import com.tngtech.configbuilder.annotations.ValueTransformer;
import com.tngtech.configbuilder.annotations.metaannotations.ValueExtractorAnnotation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnnotationUtilsTest {

    private AnnotationUtils annotationUtils;
    private Field field;
    private Class<? extends Annotation>[] annotationOrder = new Class[]{CommandLineValue.class,PropertyValue.class};

    @PropertyValue("pidFixes")
    @CommandLineValue(shortOpt = "p", longOpt = "pidFixFactory")
    @ValueTransformer(TestConfig.class)
    private Collection<String> pidFixes;

    @Before
    public void setUp() throws Exception {
        field = this.getClass().getDeclaredField("pidFixes");
        annotationUtils = new AnnotationUtils();
    }

    @Test
    public void testGetAnnotationsAnnotatedWith() throws Exception {
        List<Annotation> result = annotationUtils.getAnnotationsAnnotatedWith(field.getDeclaredAnnotations(),ValueExtractorAnnotation.class);
        assertTrue(result.contains(field.getAnnotation(CommandLineValue.class)));
        assertTrue(result.contains(field.getAnnotation(PropertyValue.class)));
        assertFalse(result.contains(field.getAnnotation(ValueTransformer.class)));
    }


    @Test
    public void testGetAnnotationsInOrder() throws Exception {
        List<Annotation> orderList = Lists.newArrayList();
        orderList.add(field.getAnnotation(CommandLineValue.class));
        orderList.add(field.getAnnotation(PropertyValue.class));
        List<Annotation> result = annotationUtils.getAnnotationsInOrder(field, annotationOrder);
        assertEquals(orderList,result);
    }

    @Test
    public void testGetFieldsAnnotatedWith() throws Exception {
        assertTrue(annotationUtils.getFieldsAnnotatedWith(this.getClass(), PropertyValue.class).contains(field));
    }
}
