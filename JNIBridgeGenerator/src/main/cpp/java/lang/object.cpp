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
#include <iostream>

#include <java/lang/object.h>
#include <com/tecnyse/integration/defines.h>
#include <com/tecnyse/integration/jni_bridge.h>

jclass java::lang::Object::java_class = 0;
jmethodID java::lang::Object::equals_id = 0;
jmethodID java::lang::Object::hash_code_id = 0;
jmethodID java::lang::Object::to_string_id = 0;
jmethodID java::lang::Object::constructor_id = 0;

static_init_helper java::lang::Object::object_init_helper = static_init_helper( &Object::initJNI );

void java::lang::Object::initJNI() {
	std::cout << "Initializing java.lang.Object bridge... ";
	JNIEnv* env = com::tecnyse::integration::JNIBridge::getJNIEnv();

	java_class = env->FindClass( "java/lang/Object" );
	CHECKERROR(env);

	equals_id = env->GetMethodID( java_class, "equals", "(Ljava/lang/Object;)Z" );
	CHECKERROR(env);
	hash_code_id = env->GetMethodID( java_class, "hashCode", "()I" );
	CHECKERROR(env);
	to_string_id = env->GetMethodID( java_class, "toString", "()Ljava/lang/String;" );
	CHECKERROR(env);
	constructor_id = env->GetMethodID( java_class, "<init>", "()V" );
	CHECKERROR(env);
	std::cout << "Done." << std::endl;
}

java::lang::Object::Object() {
	JNIEnv* env = com::tecnyse::integration::JNIBridge::getJNIEnv();

	TIMED_CALL(jobject obj = env->NewObject(java_class, constructor_id), "Object()");
	CHECKERROR(env);

	setJObject( obj );

	env->DeleteLocalRef( obj );
	CHECKERROR(env);
}

void java::lang::Object::setJObject( jobject object ) {
	JNIEnv* env = com::tecnyse::integration::JNIBridge::getJNIEnv();
	if ( object_instance ) {
		env->DeleteGlobalRef( object_instance );
	}
	TIMED_CALL( object_instance = env->NewGlobalRef( object ), "Global Ref");
}

java::lang::Object::Object( jobject object ) {
	JNIEnv* env = com::tecnyse::integration::JNIBridge::getJNIEnv();
	setJObject( object );
	CHECKERROR(env);
}

java::lang::Object::~Object() {
	JNIEnv* env = com::tecnyse::integration::JNIBridge::getJNIEnv();
	TIMED_CALL(env->DeleteGlobalRef(object_instance), "Delete Global Ref");
	CHECKERROR(env);
}

jobject java::lang::Object::getJObject() const {
	return object_instance;
}

jclass java::lang::Object::getClass() const {
	TIMED_CALL(jclass result = com::tecnyse::integration::JNIBridge::getJNIEnv()->GetObjectClass(getJObject()), "GetClass()");
	return result;
}

bool java::lang::Object::equals( const Object* aObject ) const {
	JNIEnv* env = com::tecnyse::integration::JNIBridge::getJNIEnv();
	TIMED_CALL(bool result = env->CallBooleanMethod(object_instance, equals_id, aObject->getJObject()), "Object.equals(Object)");
	CHECKERROR(env);
	return result;
}

int java::lang::Object::hashCode() const {
	JNIEnv* env = com::tecnyse::integration::JNIBridge::getJNIEnv();
	TIMED_CALL(int result = env->CallIntMethod(object_instance, hash_code_id), "Object.hashCode()");
	CHECKERROR(env);
	return result;
}

const char* java::lang::Object::toString() const {
	JNIEnv* env = com::tecnyse::integration::JNIBridge::getJNIEnv();
	TIMED_CALL(jstring java_string = (jstring) env->CallObjectMethod(object_instance, to_string_id), "Object.toString()");
	CHECKERROR(env);
	TIMED_CALL(const char* s = env->GetStringUTFChars(java_string, NULL), "GetStringUTFChars");
	TIMED_CALL(env->ReleaseStringUTFChars(java_string, s), "ReleaseStringUTFChars");
	CHECKERROR(env);
	return s;
}

void java::lang::Object::toString( std::string& target ) const {
	JNIEnv* env = com::tecnyse::integration::JNIBridge::getJNIEnv();
	TIMED_CALL(jstring java_string = (jstring) env->CallObjectMethod(object_instance, to_string_id), "Object.toString()");
	CHECKERROR(env);
	TIMED_CALL(const char* s = env->GetStringUTFChars(java_string, NULL), "GetStringUTFChars");
	target = s;
	TIMED_CALL(env->ReleaseStringUTFChars(java_string, s), "ReleaseStringUTFChars");
	CHECKERROR(env);
}
