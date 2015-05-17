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
