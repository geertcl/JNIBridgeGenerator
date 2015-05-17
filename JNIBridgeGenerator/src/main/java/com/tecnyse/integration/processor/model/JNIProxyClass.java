/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
 */
package com.tecnyse.integration.processor.model;

import com.google.common.base.Joiner;
import com.tecnyse.integration.processor.generator.GeneralGenerator;

import java.util.*;

public abstract class JNIProxyClass {

  private List<String> packageNames;
  private String className;
  private String header;
  private String qualifiedName;

  private Collection<JNIMethod> methods;

  private Collection<JNIType> dependencies;

  protected JNIProxyClass( String aQualifiedName ) {
    qualifiedName = aQualifiedName;
    int indexOfLastDot = aQualifiedName.lastIndexOf( "." );
    className = aQualifiedName.substring( indexOfLastDot + 1 );
    header = aQualifiedName.replaceAll( "\\.", "_" ).toUpperCase() + "_H";
    String[] names = aQualifiedName.substring( 0, indexOfLastDot ).split( "\\." );
    packageNames = new ArrayList<String>();
    Collections.addAll( packageNames, names );
    methods = new ArrayList<JNIMethod>();
    dependencies = new HashSet<JNIType>();
  }

  public List<String> getPackageNames() {
    return packageNames;
  }

  public String getQualifiedName() {
    return qualifiedName;
  }

  public String getClassName() {
    return className;
  }

  public String getHeader() {
    return header;
  }

  public Collection<JNIMethod> getMethods() {
    return methods;
  }

  public String getNamespace() {
    return Joiner.on( "::" ).join( packageNames );
  }

  public void addDependency( JNIType aType ) {
    dependencies.add( aType );
  }

  public Collection<JNIType> getDependencies() {
    return dependencies;
  }

  public abstract void accept( GeneralGenerator aGenerator );
}
