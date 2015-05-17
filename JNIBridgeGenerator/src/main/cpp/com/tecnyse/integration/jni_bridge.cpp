/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
 */
#include "jni_bridge.h"

#include <iostream>
#include <stdexcept>

JavaVM* com::tecnyse::integration::JNIBridge::running_jvm_instance = nullptr;

void com::tecnyse::integration::JNIBridge::checkError( JNIEnv* jni_env ) {
	jthrowable ex = jni_env->ExceptionOccurred();
	if ( ex ) {
		jni_env->ExceptionDescribe();
		throw std::runtime_error( "Exception occurred in JVM" );
	}
}

com::tecnyse::integration::JNIBridge::JNIBridge( void ) {}

com::tecnyse::integration::JNIBridge::~JNIBridge( void ) {
	std::cout << "Destroying JVM... ";
	running_jvm_instance->DestroyJavaVM();
	std::cout << "Done" << std::endl;
	running_jvm_instance = nullptr;

	std::cout << "Bridge stopped" << std::endl;
}

void com::tecnyse::integration::JNIBridge::addJvmArg( std::string jvm_arg ) {
	jvm_args.push_back( jvm_arg );
}

void com::tecnyse::integration::JNIBridge::addToClasspath( std::string jar ) {
	jars.push_back( jar );
}

void com::tecnyse::integration::JNIBridge::addLibraryPath( std::string path ) {
	library_paths.push_back( path );
}

void com::tecnyse::integration::JNIBridge::start() {
	std::string jvm_class_path;

	const size_t nb_jars = jars.size();
	if ( nb_jars == 0 ) {
		jvm_class_path = "";
	}
	else {
		jvm_class_path = "-Djava.class.path=";
		for ( size_t i = 0; i < nb_jars; i++ ) {
			jvm_class_path += jars[ i ];
#if defined(WINDOWS)
			jvm_class_path += ";";
#else
			jvm_class_path += ":";
#endif
		}
	}

	const std::string str_java_library_path = buildLibraryPath();

	const size_t nb_of_options = jvm_args.size() + 2;
	JavaVMOption* options = new JavaVMOption[nb_of_options];
	options[ 0 ].optionString = const_cast<char*>( jvm_class_path.c_str() );
	options[ 1 ].optionString = const_cast<char*>( str_java_library_path.c_str() );
	int index = 2;
	for ( std::vector<std::string>::iterator it = jvm_args.begin(); it != jvm_args.end(); ++it ) {
		options[ index++ ].optionString = const_cast<char*>( it->c_str() );
	}

	JavaVMInitArgs vm_args;
	vm_args.version = JNI_VERSION_1_6;
	vm_args.options = options;
	vm_args.nOptions = nb_of_options;
	vm_args.ignoreUnrecognized = JNI_TRUE;

	for ( int i = 0; i < nb_of_options; ++i ) {
		std::cout << "JVM Option: " << options[ i ].optionString << std::endl;
	}

	//Create the JVM
	JNIEnv* jni_env;
	jint result = JNI_CreateJavaVM(&running_jvm_instance, reinterpret_cast<void**>(&jni_env), &vm_args);

	if ( result < 0 ) {
		throw std::runtime_error( "Could not launch the JVM" );
	}

	std::cout << "JVM loaded" << std::endl;

	printProperties();

}

std::string com::tecnyse::integration::JNIBridge::buildLibraryPath() {
	const size_t nb_libs = library_paths.size();

	std::string jvm_library_path = "-Djava.library.path=";
	for ( size_t i = 0; i < nb_libs; i++ ) {
		jvm_library_path += library_paths[ i ];
#if defined(WINDOWS)
		jvm_library_path += ";";
#else
		jvm_library_path += ":";
#endif

	}

	return jvm_library_path;
}

JNIEnv* com::tecnyse::integration::JNIBridge::getJNIEnv() {
	if ( running_jvm_instance == nullptr ) {
		throw std::runtime_error("There is no JVM running!");
	}
	JNIEnv* jni_env;
	if ( running_jvm_instance->GetEnv( reinterpret_cast<void**>( &jni_env ), JNI_VERSION_1_6 ) == JNI_EDETACHED ) {
		jint attach_result = running_jvm_instance->AttachCurrentThread( reinterpret_cast<void**>( &jni_env ), nullptr );
		if ( attach_result != JNI_OK ) {
			std::cerr << "Cannot attach current thread, returned " << attach_result << std::endl;
			return nullptr;
		}
	}
	return jni_env;
}

JNIEnv* com::tecnyse::integration::JNIBridge::attachCurrentThread() {
	if ( running_jvm_instance == nullptr ) {
		throw std::runtime_error("There is no JVM running!");
	}

	JNIEnv* thread_env;

	running_jvm_instance->AttachCurrentThread( reinterpret_cast<void**>( &thread_env ), nullptr );

	return thread_env;
}

void com::tecnyse::integration::JNIBridge::detachCurrentThread() {
	if ( running_jvm_instance == nullptr ) {
		throw std::runtime_error("There is no JVM running!");
	}

	running_jvm_instance->DetachCurrentThread();
}

void com::tecnyse::integration::JNIBridge::printProperties() {
	JNIEnv* env = getJNIEnv();
	jclass system_class = env->FindClass( "java/lang/System" );
	jmethodID properties_method_id = env->GetStaticMethodID( system_class, "getProperties", "()Ljava/util/Properties;" );

	jobject j_properties = env->CallStaticObjectMethod( system_class, properties_method_id );
	jclass properties_class = env->FindClass( "java/util/Properties" );
	jmethodID to_string_id = env->GetMethodID( properties_class, "toString", "()Ljava/lang/String;" );

	jstring j_string = static_cast<jstring>( env->CallObjectMethod( j_properties, to_string_id ) );
	const char* s = env->GetStringUTFChars( j_string, nullptr );
	std::cout << "System.getProperties(): " << std::endl;
	std::cout << s << std::endl;
	env->ReleaseStringUTFChars( j_string, s );
}
