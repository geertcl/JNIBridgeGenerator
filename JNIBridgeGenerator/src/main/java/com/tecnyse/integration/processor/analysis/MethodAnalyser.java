/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
 */
package com.tecnyse.integration.processor.analysis;

import com.tecnyse.integration.processor.ModelCollector;
import com.tecnyse.integration.processor.model.JNIMethod;
import com.tecnyse.integration.processor.model.JNIMethodParameter;
import com.tecnyse.integration.processor.model.JNIType;

import javax.annotation.processing.Messager;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.util.Set;

public class MethodAnalyser extends AbstractElementAnalyser<ExecutableElement> {

  public MethodAnalyser( Messager aMessager ) {
    super( aMessager );
  }

  @Override public void analyse( ExecutableElement aElement, ModelCollector aCollector ) {
    Set<Modifier> modifiers = aElement.getModifiers();
    if ( !modifiers.contains( Modifier.PUBLIC ) || modifiers.contains( Modifier.ABSTRACT ) ) {
      getMessager().printMessage( Diagnostic.Kind.ERROR, "Skipping non public or abstract method" );
      return;
    }

    JNIType returnType = getType( aElement.getReturnType() );

    JNIMethod method = new JNIMethod( aElement.getSimpleName().toString(), returnType );

    for ( VariableElement param : aElement.getParameters() ) {
      JNIType type = getType( param );
      String name = param.getSimpleName().toString();
      method.getParameters().add( new JNIMethodParameter( type, name ) );
    }

    method.setStatic( aElement.getModifiers().contains( Modifier.STATIC ) );

    aCollector.addMethod( method );
  }
}
