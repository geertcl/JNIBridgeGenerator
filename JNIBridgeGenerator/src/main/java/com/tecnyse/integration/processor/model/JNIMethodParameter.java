/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
 */
package com.tecnyse.integration.processor.model;

public class JNIMethodParameter {
  private final JNIType type;
  private final String name;

  public JNIMethodParameter( JNIType aType, String aName ) {
    type = aType;
    name = aName;
  }

  public JNIType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  @Override public String toString() {
    return name;
  }
}
