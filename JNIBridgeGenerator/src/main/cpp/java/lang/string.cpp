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
