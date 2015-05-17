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
