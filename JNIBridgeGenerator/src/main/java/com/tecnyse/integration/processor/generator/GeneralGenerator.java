/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
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
