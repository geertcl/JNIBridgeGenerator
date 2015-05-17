/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
 */
package com.tecnyse.integration.processor.analysis;

import com.tecnyse.integration.processor.ModelCollector;
import com.tecnyse.integration.processor.model.JNIRawProxyClass;

import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class ClassAnalyser extends AbstractElementAnalyser<TypeElement> {
  public ClassAnalyser( Messager aMessager ) {
    super( aMessager );
  }

  @Override public void analyse( TypeElement aElement, ModelCollector aCollector ) {
    getMessager().printMessage( Diagnostic.Kind.NOTE, "Processing " + aElement.toString() );

    JNIRawProxyClass clazz = new JNIRawProxyClass( aElement.getQualifiedName().toString() );

    aCollector.addClass( clazz );
  }
}
