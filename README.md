# JNIBridgeGenerator

The JNI Bridge Generator allows you to generate C++ proxies for Java classes, using Java Annotation Processors and Velocity templates.

## License

The JNIBridgeGenerator is licensed under the Apache License, v2: http://www.apache.org/licenses/LICENSE-2.0.html

## Repository structure

- JNIBridgeGenerator: the IntelliJ module for the generator
- test: an IntelliJ module with samples.

## Usage

Add the `@JNIProxy` annotation on

- Classes
- Constructors
- Methods

to generate C++ proxies at compile time.

```java
package com.tecnyse.sample.integration;

import com.tecnyse.integration.JNIProxy;

@JNIProxy
public class MyClass {

  @JNIProxy
  public int foo() {
    return 1;
  }
  
  @JNIProxy
  public static String bar(int arg1, String arg2) {
    return arg2 + arg1;
  }
}
```

The above will generate 2 files in `com/tecnyse/samples/integration` (the package):

- `myclass.h`: declares the JNI proxy for `MyClass` as a C++ class in the `com::tecnyse::samples::integration` namespace.
- `myclass.cpp`: defines the JNI bindings for MyClass.

## Getting Started

- Put the JNIBridgeGenerator jar on your classpath
- Add the `com.tecnyse.integration.processor.JNIProxyProcessor` processor in your services manifest.
- Annotate.
- Compile!
