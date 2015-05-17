/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
 */
package com.tecnyse.integration.processor.model;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.util.TypeKindVisitor7;

public class JNITypeVisitor extends TypeKindVisitor7<JNIType, Void> {

  private JNIType result;

  public JNIType getResult() {
    return result;
  }

  @Override public JNIType visitNoTypeAsVoid( NoType t, Void p ) {
    result = JNIType.VOID;
    return result;
  }

  @Override public JNIType visitPrimitiveAsBoolean( PrimitiveType t, Void p ) {
    result = JNIType.BOOLEAN;
    return result;
  }

  @Override public JNIType visitPrimitiveAsByte( PrimitiveType t, Void p ) {
    result = JNIType.BYTE;
    return result;
  }

  @Override public JNIType visitPrimitiveAsShort( PrimitiveType t, Void p ) {
    result = JNIType.SHORT;
    return result;
  }

  @Override public JNIType visitPrimitiveAsInt( PrimitiveType t, Void p ) {
    result = JNIType.INT;
    return result;
  }

  @Override public JNIType visitPrimitiveAsLong( PrimitiveType t, Void p ) {
    result = JNIType.LONG;
    return result;
  }

  @Override public JNIType visitPrimitiveAsChar( PrimitiveType t, Void p ) {
    result = JNIType.CHAR;
    return result;
  }

  @Override public JNIType visitPrimitiveAsFloat( PrimitiveType t, Void p ) {
    result = JNIType.FLOAT;
    return result;
  }

  @Override public JNIType visitPrimitiveAsDouble( PrimitiveType t, Void p ) {
    result = JNIType.DOUBLE;
    return result;
  }

  @Override public JNIType visitDeclared( DeclaredType t, Void p ) {
    if ( t.asElement() instanceof TypeElement ) {
      result = new JNIType( ( ( TypeElement ) t.asElement() ).getQualifiedName().toString() );
    }
    return result;
  }
}
