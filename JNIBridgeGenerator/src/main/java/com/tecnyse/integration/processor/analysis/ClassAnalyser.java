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
