/*
 * Copyright 2015 Geert Claeys
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tecnyse.integration.processor.model;

public class JNIType {

  public static final JNIType INT = new JNIType( "int", true, "I" );
  public static final JNIType SHORT = new JNIType( "short", true, "S" );
  public static final JNIType FLOAT = new JNIType( "float", true, "F" );
  public static final JNIType DOUBLE = new JNIType( "double", true, "D" );
  public static final JNIType CHAR = new JNIType( "char", true, "C" );
  public static final JNIType BYTE = new JNIType( "unsigned char", true, "B" );
  public static final JNIType LONG = new JNIType( "long", true, "J" );
  public static final JNIType BOOLEAN = new JNIType( "bool", true, "Z" );
  public static final JNIType VOID = new JNIType( "void", true, "V" );

  private final String typeName;

  private final String signature;

  private final boolean primitive;

  private final String includeName;

  public JNIType( String aTypeName ) {
    this( aTypeName, false );
  }

  private JNIType( String aTypeName, boolean aPrimitive ) {
    this( aTypeName, aPrimitive, createSignature( aTypeName ) );
  }

  private JNIType( String aTypeName, boolean aPrimitive, String aSignature ) {
    primitive = aPrimitive;
    typeName = aPrimitive ? aTypeName : aTypeName + "*";
    includeName = aPrimitive ? null : aTypeName.replaceAll( "\\.", "/" );
    signature = aSignature;
  }

  public boolean isPrimitive() {
    return primitive;
  }

  public String getTypeName() {
    return typeName.replaceAll( "\\.", "::" );
  }

  public String getSignature() {
    return signature;
  }

  public String getIncludeName() {
    return includeName;
  }

  @Override public int hashCode() {
    return typeName.hashCode();
  }

  @Override public boolean equals( Object obj ) {
    return obj instanceof JNIType && typeName.equals( ( ( JNIType ) obj ).getTypeName() );
  }

  private static String createSignature( String aTypeName ) {
    return "L" + aTypeName.replaceAll( "\\.", "/" ) + ";";
  }
}
