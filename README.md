Java ConfigBuilder
==================

#### Table of Contents
[What is it](#what-is-it)  
[Motivation](#motivation)  
[Download](#download)  
[How To Build Your Config](#how-to-build-your-config)
[Usage example](#usage example)

How To Build Your Config
------------------------

####1. Create your class:
```java
public class Config {
    private String userName;
    private Collection<PidFix> pidFixes
    ...
}
```
####2.Annotate the class (configure the loading of properties files)

If you want the ConfigBuilder to get values from properties files, 
you can specify the filenames (without file extension or path) by 
annotating your config class with the @PropertiesFiles annotation. 
You can specify multiple filenames like this: 
> @PropertiesFiles({file1,file2,...})

By default, properties files are loaded using the PropertyLoader's default config, which 
searches for files in the current directory, the ContextClassLoader and the user's home directory.
You can manually specify the search locations by annotating your config class with the @PropertyLocations annotation, e.g.
> @PropertyLocations(directories = {"/home/user"}, resourcesForClasses={MyApp.class}, contextClassLoader = true)

The PropertyLoader also searches for files with the default suffixes, i.e. the user name, local host names and 'override'.
You can manually set the suffixes by annotating your config class with the @PropertySuffixes annotation like this:
> @PropertySuffixes(extraSuffixes = {"tngtech","myname"}, hostNames = true)

The default file extensions are .properties and .xml. You can replace the .properties file extension with your own
by annotating your config class with 
> @PropertyExtension("fileextension")

####3. Annotate the fields

#####4. 
> @DefaultValue
> @PropertyValue
> @CommandLineValue
> @ValueTransformer
> @LoadingOrder

To specify a global order for parsing ValueExtractorAnnotation annotations, annotate the class with 
LoadingOrder

To specify your own error messages file (which is loaded by the PropertyLoader with the same settings as other the properties files), annotate the class with 
ErrorMessageFile

Usage example
-------------
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
