/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
 */
package com.tecnyse.samples.integration;

import com.tecnyse.integration.JNIProxy;

@JNIProxy
public class TestFactory {

  @JNIProxy
  public Test createTest( String aParam ) {
    return new Test( 1 );
  }

  @JNIProxy
  public static Test createTest() {
    return new Test( 2 );
  }

}
