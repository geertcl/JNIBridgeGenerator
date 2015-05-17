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
package com.tecnyse.integration.processor.generator;

import com.tecnyse.integration.processor.model.JNIRawProxyClass;
import org.apache.velocity.app.VelocityEngine;

import javax.annotation.processing.ProcessingEnvironment;

public class GeneralGenerator {

  private final ProcessingEnvironment processingEnvironment;
  private final VelocityEngine engine;

  public GeneralGenerator(ProcessingEnvironment aProcessingEnvironment, VelocityEngine aEngine) {
    processingEnvironment = aProcessingEnvironment;
    engine = aEngine;
  }

  public void generate( JNIRawProxyClass aRawProxyClass ) {
    new RawProxyClassGenerator( processingEnvironment, engine ).generate( aRawProxyClass );
  }


}
