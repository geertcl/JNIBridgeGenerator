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
#ifndef OBJECT_H
#define OBJECT_H

#include <string>

#include <java/lang/object.h>

namespace java {
namespace lang {

class String : public java::lang::Object {

private:
    std::string* local_string;
    void create(const char*);

public:
    String();
    explicit String(std::string);
    explicit String(const char*);
    virtual ~String();

    String& operator= (const std::string& str);
    String& operator= (const char*);

    std::string* get();
};

}
}
