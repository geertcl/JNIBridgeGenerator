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

import com.google.common.base.Joiner;
import com.tecnyse.integration.processor.model.JNIProxyClass;
import com.tecnyse.integration.processor.model.JNIRawProxyClass;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;

public class RawProxyClassGenerator extends AbstractGenerator {

  public RawProxyClassGenerator( ProcessingEnvironment aProcessingEnvironment, VelocityEngine aEngine ) {
    super( aProcessingEnvironment, aEngine );
  }

  @Override public void generate( JNIProxyClass aProxyClass ) {
    if ( !( aProxyClass instanceof JNIRawProxyClass ) ) {
      throw new IllegalArgumentException( "I can only generate proxies for raw classes" );
    }
    VelocityContext context = new VelocityContext();

    context.put( "proxyClass", aProxyClass );

    Template headerTemplate = getEngine().getTemplate( "/com/tecnyse/integration/template/class/bridge_header_raw.vm" );
    Template implTemplate = getEngine().getTemplate( "/com/tecnyse/integration/template/class/bridge_impl_raw.vm" );

    String path = Joiner.on( "." ).join( aProxyClass.getPackageNames() );

    FileObject headerResource;
    FileObject implResource;
    try {
      headerResource = getFiler().createResource( StandardLocation.SOURCE_OUTPUT,
                                                  path,
                                                  aProxyClass.getClassName() + ".h" );
      implResource = getFiler().createResource( StandardLocation.SOURCE_OUTPUT,
                                                path,
                                                aProxyClass.getClassName() + ".cpp" );
    }
    catch ( IOException e ) {
      getMessager().printMessage( Diagnostic.Kind.ERROR,
                                  "Cannot create header for " +
                                  aProxyClass.getClassName() +
                                  "; " +
                                  e.getMessage() );
      return;
    }

    getMessager().printMessage( Diagnostic.Kind.NOTE, headerResource.getName() );
    getMessager().printMessage( Diagnostic.Kind.NOTE, implResource.getName() );

    Writer headerWriter = null;
    Writer implWriter = null;
    try {
      headerWriter = headerResource.openWriter();
      headerTemplate.merge( context, headerWriter );
      headerWriter.flush();

      implWriter = implResource.openWriter();
      implTemplate.merge( context, implWriter );
      implWriter.flush();
    }
    catch ( IOException e ) {
      getMessager().printMessage( Diagnostic.Kind.ERROR,
                                  "Cannot write header for " + aProxyClass.getClassName() );
    }
    finally {
      if ( headerWriter != null ) {
        try {
          headerWriter.close();
        }
        catch ( IOException e ) {
          getMessager().printMessage( Diagnostic.Kind.ERROR,
                                      "Cannot close writer for header for " +
                                      aProxyClass.getClassName() );
        }
      }
      if ( implWriter != null ) {
        try {
          implWriter.close();
        }
        catch ( IOException e ) {
          getMessager().printMessage( Diagnostic.Kind.ERROR,
                                      "Cannot close writer for implementation file for " +
                                      aProxyClass.getClassName() );
        }
      }
    }
  }
}
