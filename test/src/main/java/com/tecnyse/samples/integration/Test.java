/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
 */
package com.tecnyse.samples.integration;

import com.tecnyse.integration.JNIProxy;

@JNIProxy
public class Test {

  @JNIProxy
  public Test( int aParam ) {

  }

  @JNIProxy
  public int test( int aParam1, int aParam2 ) {
    return 1;
  }

}
