/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
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
