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
