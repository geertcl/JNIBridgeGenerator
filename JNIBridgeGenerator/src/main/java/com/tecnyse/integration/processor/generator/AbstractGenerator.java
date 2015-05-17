/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
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
