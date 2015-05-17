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
