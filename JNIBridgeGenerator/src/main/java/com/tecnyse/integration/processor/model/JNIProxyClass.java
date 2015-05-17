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
