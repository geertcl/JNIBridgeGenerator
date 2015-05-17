/*
 * Copyright (c) 2015 Geert Claeys, Apache License 2.0
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
