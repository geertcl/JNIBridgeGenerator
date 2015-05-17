# JNIBridgeGenerator

The JNI Bridge Generator allows you to generate C++ proxies for Java classes, using Java Annotation Processors and Velocity templates.

## License

The JNIBridgeGenerator is licensed under the Apache License, v2: http://www.apache.org/licenses/LICENSE-2.0.html

## Usage

Add the `@JNIProxy` annotation on

- Classes
- Constructors
- Methods

to generate C++ proxies at compile time.

## Getting Started

- Put the JNIBridgeGenerator jar on your classpath
- Add the `com.tecnyse.integration.processor.JNIProxyProcessor` processor in your services manifest.
- Compile!
