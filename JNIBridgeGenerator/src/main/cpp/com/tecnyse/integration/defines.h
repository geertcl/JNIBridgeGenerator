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
#ifndef DEFINES_H
#define DEFINES_H

//#define MONITOR_JNI_CALLS

#define CHECKERROR(p) com::tecnyse::integration::JNIBridge::checkError(p)

#if defined(_WIN32) || defined(WIN32) || defined(_WIN64) || defined(WIN64)
#define WINDOWS
#endif

#ifdef MONITOR_JNI_CALLS
#include <ctime>
#include <iostream>

static clock_t logging_timer_clock;

#define TIMED_CALL(call, description) \
    logging_timer_clock = clock(); \
    call; \
    logging_timer_clock = clock() - logging_timer_clock; \
    printf("Execution of '%s' took took %f milliseconds \n", description, ((float)logging_timer_clock)/CLOCKS_PER_SEC*1000); \
    fflush(stdout)


#else

#define TIMED_CALL(call, f) call

#endif

#endif
