package com.tngtech.propertyloader;

import com.tngtech.propertyloader.exception.PropertyLoaderException;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PropertyLoaderIntegrationTest {

    @Test
    void testLoadingFromDefaultLocationsOrFullPath() {
        URL urls = this.getClass().getResource("/abc.def.properties");
        String abcdefWithFullPath = urls.getPath().replace(".properties", "");
        String[] args = {"toBeIncluded", "src/test/resources/testUmlauts", abcdefWithFullPath};

        PropertyLoader propertyLoader = new PropertyLoader().withDefaultConfig();
        Properties properties = propertyLoader.load(args);

        assertThat(properties).containsKeys(
                "definedInIncluded",  // loaded from context classpath
                "umlauts",  // loaded from relative to current directory (no leading /)
                "abc"  // loaded with all FileOpeners because of correct full path (leading /)
        );
    }

    @Test
    void testLoadingFromContextClassLoaderOnly() {
        String[] args = {"toBeIncluded", "/abc.def"};

        PropertyLoader propertyLoader = new PropertyLoader()
                .atContextClassPath()
                .addDefaultSuffixes()
                .withDefaultFilters();
        Properties properties = propertyLoader.load(args);

        assertThat(properties)
                .containsKey("definedInIncluded")  // is loaded from context classpath
                .doesNotContainKey("abc");  // not found by classpath opener because of leading slash
    }

    @Test
    void testLoadingFromCurrentDirectoryOnly() {
        String[] args = {"toBeIncluded", "src/test/resources/testUmlauts", "/src/test/resources/abc.def"};

        PropertyLoader propertyLoader = new PropertyLoader()
                .atCurrentDirectory()
                .addDefaultSuffixes()
                .withDefaultFilters();
        Properties properties = propertyLoader.load(args);

        assertThat(properties)
                .doesNotContainKey("definedInIncluded")  // no loading from classpath etc
                .containsKey("umlauts")  // correct path relative to current directory
                .doesNotContainKey("abc");  // not found because of leading slash but not a correct path
    }

    @Test
    void testLoadingWithDefaultConfig_Loads_Includes_And_Resolves_Variables() {
        String[] args = {"testForIncludesAndVariableResolving"};

        PropertyLoader propertyLoader = new PropertyLoader().withDefaultConfig();
        Properties properties = propertyLoader.load(args);

        assertThat(properties)
                .containsEntry("b", "Hello, World!")  // b is variable a
                .containsEntry("xxx", "yes")  // xxx is defined in toBeIncluded
                .containsEntry("testInclude.prod", "prod-blub");  // has to be defined, otherwise filter warns
    }

    @Test
    void testLoadingWithDefaultConfig_Throws_Exception_On_Recursive_Includes() {
        String[] args = {"testForRecursiveIncludes1"};

        PropertyLoader propertyLoader = new PropertyLoader().withDefaultConfig();

        assertThatThrownBy(() -> propertyLoader.load(args))
                .isInstanceOf(PropertyLoaderException.class);
    }

    @Test
    void testLoadingWithDefaultConfig_Does_Pop_FileNameStack() {
        String[] args = {"testForDoubleIncludes1"};

        PropertyLoader propertyLoader = new PropertyLoader().withDefaultConfig();
        Properties properties = propertyLoader.load(args);

        assertThat(properties).containsKey("definedInIncluded");
    }
}
