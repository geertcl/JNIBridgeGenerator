/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
 */
package com.tecnyse.integration.processor.analysis;

import com.tecnyse.integration.processor.ModelCollector;
import com.tecnyse.integration.processor.model.JNIType;
import com.tecnyse.integration.processor.model.JNITypeVisitor;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

public abstract class AbstractElementAnalyser<T extends Element> {

  private final Messager messager;

  protected AbstractElementAnalyser( Messager aMessager ) {
    messager = aMessager;
  }

  protected Messager getMessager() {
    return messager;
  }

  protected JNIType getType( VariableElement aElement ) {
    return getType( aElement.asType() );
  }

  protected JNIType getType( TypeMirror aType ) {
    JNITypeVisitor visitor = new JNITypeVisitor();
    aType.accept( visitor, null );
    return visitor.getResult();
  }

  public abstract void analyse( T aElement, ModelCollector aCollector );
}
