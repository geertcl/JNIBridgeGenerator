/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
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
