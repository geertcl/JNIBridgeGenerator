/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
 */
#ifndef OBJECT_H
#define OBJECT_H

#include <jni.h>
#include <string>

#include <com/tecnyse/integration/static_init.h>

namespace java {
namespace lang {

class Object {
	static jclass java_class;

	jobject object_instance;

protected:
	static jmethodID equals_id;
	static jmethodID hash_code_id;
	static jmethodID constructor_id;
	static jmethodID to_string_id;

	void setJObject(jobject);

public:
	Object();
	explicit Object( jobject );
	virtual ~Object();

	virtual jobject getJObject() const;
	virtual jclass getClass() const;

	virtual bool equals( const Object* ) const;
	virtual int hashCode() const;
	virtual void toString( std::string& ) const;
	virtual const char* toString() const;

	static static_init_helper object_init_helper;
	static void initJNI();
};

}
}


#endif // OBJECT_H
