[![Build Status](https://github.com/TNG/property-loader/actions/workflows/build.yml/badge.svg)](https://github.com/TNG/property-loader/actions/workflows/build.yml?query=branch%3Amaster)
[![Coverage Status](https://codecov.io/gh/TNG/property-loader/branch/master/graphs/badge.svg)](https://app.codecov.io/gh/TNG/property-loader)
[![Maven Central](https://img.shields.io/maven-central/v/com.tngtech.java/property-loader.svg)](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.tngtech.java%22%20AND%20a%3A%22property-loader%22)
[![Javadocs](https://javadoc.io/badge/com.tngtech.java/property-loader.svg)](https://javadoc.io/doc/com.tngtech.java/property-loader)

# Property Loader

## Table of contents

[What Is It](#what-is-it)  
[Quick Start](#quick-start)  
[Postprocessing Features](#postprocessing-features)  
[Advanced Configuration](#advanced-configuration)  
[Javadoc](#javadoc)  
[How To Contribute](#how-to-contribute)  

## What is it

The Property Loader is a Java library for managing property configurations.

It supports several property loading strategies and features e.g.:

1. Hierarchical/Ordered property loading (Master, User, System properties ....)
2. Property templates
3. Recursive property resolution
4. Property encryption

## Quick start

1. Get the code:
```
$ git clone git@github.com:TNG/property-loader.git
```
2. Build the library:
```
$ mvn install
```
3. Include jar file to your application
4. Add property files e.g. `props.properties` to your application's classpath
5. Load properties:

```java
PropertyLoader propertyLoader = new PropertyLoader().withDefaultConfig();

// loads: "props.properties", "props.$hostname.properties", "props.$user.properties"
Properties properties = propertyLoader.load("props")
```

## Postprocessing features

### 1. Variable resolving

Variables in property values can be defined with:

```
{$...}
```
Even nested variables are allowed, e.g.:

```
{$var{$innervar}e}
```
In the case above, the PropertyLoader will first resolve the inner variable, replace it, then resolve the outer variable.

### 2. Includes

In order to include additional properties files from a properties file, add `%include` as a key with the file basenames
separated by commas as its value, e.g.:

```
%include=file1,file2,file3
```

### 3. Decryption

Encrypted property values that are prefixed with `DECRYPT:` will be decrypted after loading.

## Advanced configuration

The PropertyLoader's default configuration includes default search paths, suffixes and applies all available postprocessing filters.

The default search paths are:
1. the current directory
2. the user's home directory
3. the context classpath

The default suffixes are:
1. local host names
2. the user name
3. 'override'

### 1. Search locations

Tell the PropertyLoader to search for properties files at its default locations (user's home directory, current directory and context classpath):

```java
propertyLoader.atDefaultLocations()
```

#### 1.1 Folders and URLs

Tell the PropertyLoader to search for properties files at custom paths:

```java
propertyLoader.atDirectory(String directory)
propertyLoader.atBaseURL(URL url)
propertyLoader.atCurrentDirectory()
propertyLoader.atHomeDirectory()
```

#### 1.2 Classpath

Tell the PropertyLoader to search for properties files in the current thread's classpath:

```java
propertyLoader.atContextClassPath()
```

This will get the classloader from the current thread and use it to find properties files.

#### 1.3 Classloader

Tell the PropertyLoader to search for properties files using a custom ClassLoader:

```java
propertyLoader.atClassLoader(ClassLoader classLoader)
```

#### 1.4 Relative to a class

Tell the PropertyLoader to search for properties files relative to the location of a class:

```java
propertyLoader.atRelativeToClass(Class<?> clazz)
```

### 2. Suffixes

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

### 3. Postprocessing

You can define which postprocessing filters are applied (includes are always processed if the key is present):

```java
propertyLoader.withDefaultFilters()
propertyLoader.withVariableResolvingFilter()
propertyLoader.withEnvironmentResolvingFilter()
propertyLoader.withDecryptingFilter()
propertyLoader.withWarnIfPropertyHasToBeDefined()
propertyLoader.withWarnOnSurroundingWhitespace()
```

## Javadoc

Full Javadoc of the code can be found here http://tng.github.io/property-loader/.

## How to contribute

Please have a look at [CONTRIBUTING.md](CONTRIBUTING.md).
