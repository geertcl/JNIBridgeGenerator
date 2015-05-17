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

import java.util.ArrayList;
import java.util.List;

public class JNIMethod {

  private final String name;
  private final JNIType returnType;
  private final List<JNIMethodParameter> parameters;
  private boolean staticMethod;

  public JNIMethod( String aName, JNIType aReturnType ) {
    name = aName;
    returnType = aReturnType;
    parameters = new ArrayList<JNIMethodParameter>();
  }

  public String getName() {
    return name;
  }

  public JNIType getReturnType() {
    return returnType;
  }

  public List<JNIMethodParameter> getParameters() {
    return parameters;
  }

  public String getSignature() {
    StringBuilder signature = new StringBuilder();
    signature.append( "(" );
    for ( JNIMethodParameter parameter : parameters ) {
      signature.append( parameter.getType().getSignature() );
    }
    signature.append( ")" );
    signature.append( returnType.getSignature() );
    return signature.toString();
  }

  public void setStatic( boolean aStatic ) {
    staticMethod = aStatic;
  }

  public boolean isStatic() {
    return staticMethod;
  }

  public String getUniqueName() {
    return name + "_" + Joiner.on( "_" ).join( parameters );
  }
}
