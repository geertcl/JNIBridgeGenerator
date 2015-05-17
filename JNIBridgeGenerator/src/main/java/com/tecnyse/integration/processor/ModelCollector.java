/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
 */
package com.tecnyse.integration.processor;

import com.tecnyse.integration.processor.model.JNIMethod;
import com.tecnyse.integration.processor.model.JNIMethodParameter;
import com.tecnyse.integration.processor.model.JNIProxyClass;
import com.tecnyse.integration.processor.model.JNIType;

import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.HashMap;
import java.util.Map;

public class ModelCollector {

  private final Messager messager;
  Map<JNIType, JNIProxyClass> proxyClasses = new HashMap<JNIType, JNIProxyClass>();

  JNIProxyClass last = null;

  public ModelCollector( Messager aMessager ) {
    messager = aMessager;
  }

  public void addClass( JNIProxyClass aClass ) {
    messager.printMessage( Diagnostic.Kind.NOTE, "Adding class " + aClass.getClassName() );
    proxyClasses.put( new JNIType( aClass.getQualifiedName() ), aClass );
    last = aClass;
  }

  public void addMethod( JNIMethod aMethod ) {
    messager.printMessage( Diagnostic.Kind.NOTE, "Adding method " + aMethod.getName() );
    if ( last == null ) {
      messager.printMessage( Diagnostic.Kind.ERROR,
                             "No class for annotated method " + aMethod.getName() + ". Is the class annotated?" );
      return;
    }
    last.getMethods().add( aMethod );
    if ( !aMethod.getReturnType().isPrimitive() ) {
      last.addDependency( aMethod.getReturnType() );
    }
    for ( JNIMethodParameter jniMethodParameter : aMethod.getParameters() ) {
      if ( !jniMethodParameter.getType().isPrimitive() ) {
        last.addDependency( jniMethodParameter.getType() );
      }
    }
  }

  public void addConstructor() {

  }

  public Iterable<? extends JNIProxyClass> getClasses() {
    return proxyClasses.values();
  }
}
