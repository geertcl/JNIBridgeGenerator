/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
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
