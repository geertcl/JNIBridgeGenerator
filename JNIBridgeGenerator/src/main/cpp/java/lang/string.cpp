/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
 */
#include <iostream>
#include <string>

#include <java/lang/object.h>
#include <com/tecnyse/integration/defines.h>
#include <com/tecnyse/integration/jni_bridge.h>

java::lang::String() : String(std::string()) {}

java::lang::String(const std::string& str) : String(str.c_str()) {}

java::lang::String(const char* chars) {
    create(chars);
}

~java::lang::String() {
    if (local_string) {
        delete local_string;
    }
}

void java::lang::String::create(const char* chars) {
    local_string = new std::string(chars);
    JNIEnv* env = com::tecnyse::integration::JNIBridge::getJNIEnv();
    jstring str = env->NewStringUTF( chars );
    setJObject(str);
}

java::lang::String& java::lang::String::operator= (const std::string& str) {
    if ( this != &str ) {
        create(str.c_str());
    }
    return *this;
}

java::lang::String& java::lang::String::operator= (const char* chars) {
    if ( this != &str ) {
        create(chars);
    }
    return *this;
}

std::string* java::lang::String::get() {
    return local_string;
}
