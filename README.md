Java ConfigBuilder
==================

#### Table of Contents
[What It Is](#what-is-it)  
[Motivation](#motivation)    
[How To Build Your Config](#how-to-build-your-config)  
[Usage example](#usage-example)  
[Java Doc](#java-doc)  

What It Is
----------

The ConfigBuilder makes use of annotations and reflections in order to build configured instances of custom classes. 

Its features include   
1. defining default values and loading of values from properties files and the command line  
2. configuring of not only String values, but fields of arbitrary types   
3. JSR303 validation of the instances it builds.  

Motivation
----------

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
####2. Annotate the class (configure the loading of properties files)

If you want the ConfigBuilder to get values from properties files, 
you can specify the files' basenames (no file extension or path) by 
annotating your config class with the @PropertiesFiles annotation. 
You can specify multiple basenames like this: 
```java
@PropertiesFiles({file1,file2,...})
```

By default, properties files are loaded using the PropertyLoader's default config, which 
searches for files in the current directory, the ContextClassLoader and the user's home directory.
You can manually specify the search locations by annotating your config class with the @PropertyLocations annotation, e.g.
```java
@PropertyLocations(directories = {"/home/user"}, resourcesForClasses={MyApp.class}, contextClassLoader = true)
```

The PropertyLoader also searches for files with the default suffixes, i.e. the user name, local host names and 'override'.
You can manually set the suffixes by annotating your config class with the @PropertySuffixes annotation like this:
```java
@PropertySuffixes(extraSuffixes = {"tngtech","myname"}, hostNames = true)
```

The default file extensions are .properties and .xml. You can replace the .properties file extension with your own
by annotating your config class with 
```java
@PropertyExtension("fileextension")
```

####3. Annotate the fields

#####3.1 Get the String value
There are three annotations that specify where the String value that configures a field comes from:
```java
@DefaultValue("value")
@PropertyValue("property.key")
@CommandLineValue(shortOpt = "o", longOpt = "option")
```

By default, any value found on the command line overwrites a value found in properties, which in turn overwrites the default value.
This order can be customized, see [4.](#4-change-the-order-in-which-annotations-are-processed-and-use-your-own-error-messages).

#####3.2 Transform it to any object
Fields don't have to be Strings. You can have any type and configure it, if you annotate the field with the
@ValueTransformer annotation and specify a class that implements the (see)FieldValueProvider interface, like so:
```java
@ValueTransformer(MyFieldValueProvider.class)
private AnyType fieldOfAnyType;
```

The MyFieldValueProvider.class is an inner class of your config and implements the getValue method:
```java
public class Config {
    public static class MyFieldValueProvider implements FieldValueProvider<AnyType> {
        public AnyType getValues(String optionValue) {
            <...>
        }
    }
    ...
}
```
####4. Change the order in which annotations are processed and use your own error messages

You can change the order in which annotations are processed glabally or individually for each field.
To specify a global order for parsing ValueExtractorAnnotation annotations, annotate the class with the
@LoadingOrder annotation. To change the order for a certain field, annotate the field.
The order may only contain ValueExtractorAnnotations, i.e. 
CommandLineValue.class, PropertyValue.class and DefaultValue.class. Example:
```java
@LoadingOrder({PropertyValue.class, CommandLineValue.class, DefaultValue.class})
```

To specify your own error messages file (which is loaded by the PropertyLoader with the same settings as other the properties files), 
annotate the class with the @ErrorMessageFile annotation:
```java
@ErrorMessageFile("myErrorMessages")
```

####5. Build an instance of your class
```java
Config myConfig = new ConfigBuilder<Config>(Config.class).withCommandLineArgs(args).build();
```

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
Config myConfig = new ConfigBuilder<Config>(Config.class).withCommandLineArgs(args).build();
```

Java Doc
--------
