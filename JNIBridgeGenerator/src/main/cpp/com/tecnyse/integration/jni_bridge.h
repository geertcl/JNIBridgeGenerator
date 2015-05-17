/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
 */
#ifndef BRIDGESETUP_H
#define BRIDGESETUP_H

#include <string>
#include <jni.h>
#include <vector>
#include <com/tecnyse/integration/defines.h>

namespace com {
namespace tecnyse {
namespace integration {

class JNIBridge {

public:
	JNIBridge( void );
	virtual ~JNIBridge( void );
	void addToClasspath( std::string );
	void start();
	void addJvmArg( std::string );
	void addLibraryPath( std::string );

	static JNIEnv* getJNIEnv();
	static JNIEnv* attachCurrentThread();
	static void detachCurrentThread();

	static void checkError( JNIEnv* );

	void printProperties();
private:

	typedef jint (JNICALL * CreateJavaVM)( JavaVM**, void**, void* );

	static JavaVM* running_jvm_instance;

	std::vector<std::string> jars;
	std::vector<std::string> jvm_args;
	std::vector<std::string> library_paths;

	std::string buildLibraryPath();
};
}
}

}
#endif
