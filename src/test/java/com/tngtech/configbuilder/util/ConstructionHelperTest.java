package com.tngtech.configbuilder.util;

import com.tngtech.configbuilder.configuration.ErrorMessageSetup;
import com.tngtech.configbuilder.exception.NoConstructorFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConstructionHelperTest {

    private static class TestConfig1 {
        public TestConfig1(){
        }
    }
    private static class TestConfig2 {
        public TestConfig2(String string, Integer integer){
        }
    }
    private static class TestConfig3 {
        public TestConfig3(String string, int i){
        }
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private ErrorMessageSetup errorMessageSetup;

    @Before
    public void setUp() throws Exception {
        when(errorMessageSetup.getErrorMessage(NoConstructorFoundException.class)).thenReturn("NoConstructorFoundException");
    }

    @Test
    public void testFindSuitableConstructor() throws Exception {
        ConstructionHelper<TestConfig2> constructionHelper = new ConstructionHelper<>(errorMessageSetup);
        constructionHelper.findSuitableConstructor(TestConfig2.class, "string", 3);
    }

    @Test
    public void testFindSuitableConstructorThrowsException() throws Exception {
        expectedException.expect(NoConstructorFoundException.class);
        expectedException.expectMessage("NoConstructorFoundException");
        ConstructionHelper<TestConfig3> constructionHelper = new ConstructionHelper<>(errorMessageSetup);
        constructionHelper.findSuitableConstructor(TestConfig3.class, "string", 3);
    }
}
