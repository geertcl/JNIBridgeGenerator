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

import com.tecnyse.integration.processor.model.JNIProxyClass;
import org.apache.velocity.app.VelocityEngine;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;

public abstract class AbstractGenerator {

  private final ProcessingEnvironment processingEnvironment;
  private final VelocityEngine engine;

  protected AbstractGenerator( ProcessingEnvironment aProcessingEnvironment, VelocityEngine aEngine ) {
    processingEnvironment = aProcessingEnvironment;
    engine = aEngine;
  }

  protected Messager getMessager() {
    return processingEnvironment.getMessager();
  }

  protected Filer getFiler() {
    return processingEnvironment.getFiler();
  }

  public VelocityEngine getEngine() {
    return engine;
  }

  public abstract void generate( JNIProxyClass aProxyClass );

}
