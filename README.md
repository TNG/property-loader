Property loader [![Build Status](https://travis-ci.org/TNG/property-loader.png?branch=master)](https://travis-ci.org/TNG/property-loader)
===============

#### Table of Contents
[What It Is](#what-is-it)    
[Quick Start](#quick-start)  
[Postprocessing Features](#postprocessing-features)   
[Advanced Configuration](#advanced-configuration)  
[Usage example](#usage-example)  
[Java Doc](#java-doc)  

What It Is
----------

The property loader is a java library for managing property configurations.

It supports several property loading strategies and features e.g.:

1. Hierarchical/Ordered Property Loading (Master, User, System properties ....)
2. Property templates
3. Recursive Property Resolution
4. Property encryption

Quick Start
-----------

1. Build the lib:
```
$ maven install
```
2. Include jar file to your application
3. Add property files eg props.properties to your applications classpath
4. Load properties:

```java
PropertyLoader propertyLoader = new PropertyLoader().withDefaultConfig();

//loads: "props.properties", "props.$hostname.properties", "props.$user.properties" in this order
Properties properties = propertyLoader.load("props")
```

Postprocessing Features
-----------------------

####1. Variable Resolving

Variables in property values can be defined with
```
{$...}
```
Even nested variables are allowed, e.g.:
```
{$var{$innervar}e}
```
In the case above, the PropertyLoader will first resolve the inner variable, replace it, then resolve the outer variable.

####2. Includes

In order to include additional properties files from a properties files, add %include as a key with the file basenames
separated by commas as its value, e.g.:
```
%include=file1,file2,file3
```

####3. Decryption

Encrypted property values that are prefixed with
```
DECRYPT:
```
will be decrypted after loading.


Advanced Configuration
----------------------

The PropertyLoader's default configuration includes default search paths, suffixes and applies all available postprocessing filters.

The default search paths are:   
1. the current directory   
2. the user's home directory   
3. the context classpath   

The default suffixes are:   
1. local host names   
2. the user name   
3. 'override'   

####1. Search Locations
Tell the PropertyLoader to search for properties files at its default locations (/home/user, current directory and context classpath):
```java
propertyLoader.atDefaultLocations()
```

#####1.1 Folders and URLs
Tell the PropertyLoader to search for properties files at custom paths:
```java
propertyLoader.atDirectory(String directory)
propertyLoader.atBaseURL(URL url)
propertyLoader.atCurrentDirectory()
propertyLoader.atHomeDirectory()
```

#####1.2 Classpath
Tell the PropertyLoader to search for properties files in the current thread's classpath:
```java
propertyLoader.atContextClassPath()
```
This will get the classloader from the current thread and use it to find properties files.

#####1.3 Classloader
Tell the PropertyLoader to search for properties files using a custom ClassLoader:
```java
propertyLoader.atClassLoader(ClassLoader classLoader)
```

#####1.4 Relative To A Class
Tell the PropertyLoader to search for properties files relative to the location of a class:
```java
propertyLoader.atRelativeToClass(Class<?> clazz)
```

####2. Suffixes
Tell the PropertyLoader to use default suffixes (local host names, user name and "override"):
```java
propertyLoader.addDefaultSuffixes()
```
Tell the PropertyLoader to use custom suffixes:
```java
propertyLoader.addSuffix(String directory)
propertyLoader.addSuffixList(List<String> suffixes)
propertyLoader.addUserName()
propertyLoader.addLocalHostNames()
```

####3. Postprocessing

You can define which postprocessing filters are applied (includes are always processed if the key is present):
```java
propertyLoader.withDefaultFilters() 
propertyLoader.withVariableResolvingFilter() 
propertyLoader.withEnvironmentResolvingFilter() 
propertyLoader.withDecryptingFilter()
propertyLoader.withWarnIfPropertyHasToBeDefined() 
propertyLoader.withWarnOnSurroundingWhitespace() 
```


Usage example
-------------

Java Doc
--------

Full Java Doc of the code can be found here http://tng.github.io/property-loader/
