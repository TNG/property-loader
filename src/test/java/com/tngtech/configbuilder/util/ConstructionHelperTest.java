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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConstructionHelperTest {

    private static class TestConfig {
        private String string;
        private Integer integer;
        public TestConfig(String string, Integer integer){
            this.string = string;
            this.integer = integer;
        }
        private Integer getInteger() {
            return integer;
        }
        private String getString() {
            return string;
        }
    }
    private static class TestConfigForException {
        public TestConfigForException(String string, int i){
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
    public void testGetInstance() throws Exception {
        ConstructionHelper<TestConfig> constructionHelper = new ConstructionHelper<>(errorMessageSetup);
        TestConfig testConfig = constructionHelper.getInstance(TestConfig.class, "string", 3);
        assertEquals("string", testConfig.getString());
        assertEquals(3,(long) testConfig.getInteger());
    }

    @Test
    public void testGetInstanceThrowsException() throws Exception {
        expectedException.expect(NoConstructorFoundException.class);
        expectedException.expectMessage("NoConstructorFoundException");
        ConstructionHelper<TestConfigForException> constructionHelper = new ConstructionHelper<>(errorMessageSetup);
        constructionHelper.getInstance(TestConfigForException.class, "string", 3);
    }
}
