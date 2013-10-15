# Property loader [![Build Status](https://travis-ci.org/TNG/property-loader.png?branch=master)](https://travis-ci.org/TNG/property-loader)

The property loader is a java library for managing property configurations.

It supports several property loading strategies and features e.g.:

1. Hierarchical/Ordered Property Loading (Master, User, System properties ....)

2. Property templates

3. Recursive Property Resolution

4. Property encryption

### Using it ###

1. Build the lib

$ gradle assemble

2. Include jar file to your application

3. Add property files eg props.properties to your applications classpath

4. Load properties

```java
        loader = PropertyUtil.defaultConfig();

        // loads: "props.properties", "props.$hostname.properties", "props.$user.properties" in this order
        Properties props = loader.load("test", "properties");
```

### JavaDoc###

Full javadoc of the code can be found here http://tng.github.io/property-loader/
