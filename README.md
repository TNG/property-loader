Java ConfigBuilder
==================

#### Table of Contents
[What is it](#what-is-it)  
[Motivation](#motivation)  
[Download](#download)  
[Usage example](#usage)

Say you have a config class that looks like this:
```java
@PropertyLocations(directories = {"/home/user"}, resourcesForClasses={MyApp.class}, contextClassLoader = true)
@PropertySuffixes(extraSuffixes = {"tngtech","myname"}, hostNames = true)
@PropertyExtension("properties")
@PropertiesFiles("config")    // Uses "config.properties", "config.<hostname>.properties", etc.
@ErrorMessageFile("config.errormessages") // Uses "config.errormessages.properties" for i18n error messages
public class Config {
    public static class PidFixFactory implements CollectionProvider<PidFix> {
        @Override
        public Collection<PidFix> getValues(String optionValue) {
            <...>
        }
    }
 
    @DefaultValue("user") // Default value is "user"
    @PropertyValue("user.name")   // Maps to the field "user.name" in the properties file
    @CommandLineValue(shortOpt="u", longOpt="user", required=true)  // Command line arguments (required option "-u/--user"
    @NotEmpty("username.notEmpty")    // JSR-303 validation (Field should not be empty)
    private String userName;
 
    @ValueProvider(PidFixFactory.class)
    @CommandLineValue(shortOpt="p", longOpt="pidFixes")
    private Collection<PidFix> pidFixes
 
    ...
}
```
To build a configured instance, simply call
```java
Config myConfig = new ConfigBuilder(Config.class).withCommandLineArgs(args).build();
```
