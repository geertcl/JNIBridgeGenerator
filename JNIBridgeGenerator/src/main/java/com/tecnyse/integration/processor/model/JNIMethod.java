/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
 */
package com.tecnyse.integration.processor.model;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;

public class JNIMethod {

  private final String name;
  private final JNIType returnType;
  private final List<JNIMethodParameter> parameters;
  private boolean staticMethod;

  public JNIMethod( String aName, JNIType aReturnType ) {
    name = aName;
    returnType = aReturnType;
    parameters = new ArrayList<JNIMethodParameter>();
  }

  public String getName() {
    return name;
  }

  public JNIType getReturnType() {
    return returnType;
  }

  public List<JNIMethodParameter> getParameters() {
    return parameters;
  }

  public String getSignature() {
    StringBuilder signature = new StringBuilder();
    signature.append( "(" );
    for ( JNIMethodParameter parameter : parameters ) {
      signature.append( parameter.getType().getSignature() );
    }
    signature.append( ")" );
    signature.append( returnType.getSignature() );
    return signature.toString();
  }

  public void setStatic( boolean aStatic ) {
    staticMethod = aStatic;
  }

  public boolean isStatic() {
    return staticMethod;
  }

  public String getUniqueName() {
    return name + "_" + Joiner.on( "_" ).join( parameters );
  }
}
