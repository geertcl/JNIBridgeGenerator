/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
 */
package com.tecnyse.integration.processor;

import com.tecnyse.integration.processor.generator.GeneralGenerator;
import com.tecnyse.integration.processor.model.*;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes(
    {"com.tecnyse.integration.JNIProxy"}
)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class JNIProxyProcessor extends AbstractProcessor {

  @Override public boolean process( Set<? extends TypeElement> annotations, RoundEnvironment roundEnv ) {
    ModelCollector collector = new ModelCollector( processingEnv.getMessager() );

    processingEnv.getMessager().printMessage( Diagnostic.Kind.NOTE, "Start processing JNI Proxies" );
    JNIElementVisitor elementVisitor = new JNIElementVisitor( processingEnv.getMessager() );
    for ( TypeElement annotation : annotations ) {
      for ( Element element : roundEnv.getElementsAnnotatedWith( annotation ) ) {
        element.accept( elementVisitor, collector );
      }
    }
    VelocityEngine engine = new VelocityEngine();
    engine.setProperty( RuntimeConstants.RESOURCE_LOADER, "classpath" );
    engine.setProperty( "classpath.resource.loader.class", ClasspathResourceLoader.class.getName() );
    engine.init();

    GeneralGenerator generalGenerator = new GeneralGenerator( processingEnv, engine );

    for ( JNIProxyClass proxyClass : collector.getClasses() ) {
      proxyClass.accept( generalGenerator );
    }
    return true;
  }


}
