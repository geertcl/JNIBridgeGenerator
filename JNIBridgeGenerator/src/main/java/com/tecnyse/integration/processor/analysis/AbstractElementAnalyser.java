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
