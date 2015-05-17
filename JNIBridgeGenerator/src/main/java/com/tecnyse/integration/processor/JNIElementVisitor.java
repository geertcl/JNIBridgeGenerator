/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
 */
package com.tecnyse.integration.processor;

import com.tecnyse.integration.processor.analysis.ClassAnalyser;
import com.tecnyse.integration.processor.analysis.MethodAnalyser;

import javax.annotation.processing.Messager;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;

public class JNIElementVisitor implements ElementVisitor<Void, ModelCollector> {

  private final Messager messager;
  private MethodAnalyser methodAnalyser;
  private ClassAnalyser classAnalyser;

  public JNIElementVisitor( Messager aMessager ) {
    messager = aMessager;
    methodAnalyser = new MethodAnalyser( aMessager );
    classAnalyser = new ClassAnalyser( aMessager );
  }

  @Override public Void visit( Element e, ModelCollector aCollector ) {
    messager.printMessage( Diagnostic.Kind.NOTE, "Processing just an element" );
    return null;
  }

  @Override public Void visit( Element e ) {
    messager.printMessage( Diagnostic.Kind.NOTE, "Processing just an element" );
    return null;
  }

  @Override public Void visitPackage( PackageElement e, ModelCollector aCollector ) {
    messager.printMessage( Diagnostic.Kind.NOTE, "Processing a package" );
    return null;
  }

  @Override public Void visitType( TypeElement e, ModelCollector aCollector ) {
    messager.printMessage( Diagnostic.Kind.NOTE, "Processing a Type" );
    classAnalyser.analyse( e, aCollector );
    return null;
  }

  @Override public Void visitVariable( VariableElement e, ModelCollector aCollector ) {
    messager.printMessage( Diagnostic.Kind.NOTE, "Processing a variable" );
    return null;
  }

  @Override public Void visitExecutable( ExecutableElement e, ModelCollector aCollector ) {
    messager.printMessage( Diagnostic.Kind.NOTE, "Processing a method" );
    if ( e.getKind() == ElementKind.METHOD ) {
      methodAnalyser.analyse( e, aCollector );
    }
    else if ( e.getKind() == ElementKind.CONSTRUCTOR ) {

    }
    return null;
  }

  @Override public Void visitTypeParameter( TypeParameterElement e, ModelCollector aCollector ) {
    messager.printMessage( Diagnostic.Kind.NOTE, "Processing a parameter" );
    return null;
  }

  @Override public Void visitUnknown( Element e, ModelCollector aCollector ) {
    messager.printMessage( Diagnostic.Kind.NOTE, "Processing something unknown" );
    return null;
  }
}
