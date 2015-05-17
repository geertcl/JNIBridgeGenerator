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
#ifndef STATIC_INIT_H
#define STATIC_INIT_H

#include <vector>

// todo: move out of the global namespace

typedef void (* init_func_type)();

class static_init {
public:
	static static_init& instance() {
		static static_init inst;
		return inst;
	}

	void add_init_func( init_func_type f ) {
		functions.push_back( f );
	}

	static void execute() {
		static_init& inst = instance();
		for ( std::vector<init_func_type>::iterator it = inst.functions.begin() ; it != inst.functions.end(); ++it ) {
			( *it )();
		}
	}

private:
	static_init() {}

	std::vector<init_func_type> functions;
};

class static_init_helper {
public:
	explicit static_init_helper( init_func_type f ) {
		static_init::instance().add_init_func( f );
	}
};

#endif
