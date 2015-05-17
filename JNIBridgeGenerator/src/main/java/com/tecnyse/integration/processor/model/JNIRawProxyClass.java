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

import com.tecnyse.integration.processor.generator.GeneralGenerator;

public class JNIRawProxyClass extends JNIProxyClass {

  public JNIRawProxyClass( String aQualifiedName ) {
    super( aQualifiedName );
  }

  @Override public void accept( GeneralGenerator aGenerator ) {
    aGenerator.generate( this );
  }
}
