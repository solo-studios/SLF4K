/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2021-2022 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file Logger.kt is part of SLF4K
 * Last modified on 20-11-2022 01:14 p.m.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * SLF4K IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.slf4j.kotlin

import kotlin.reflect.KClass

/**
 * Constructs a new logger for the invoking class.
 *
 * @return A new [KLogger] for the invoking class.
 */
public inline fun <reified T : Any> getLogger(): KLoggerDelegate<T> {
    return getLogger(T::class)
}

/**
 * Constructs a new logger using the provided class.
 *
 * @param kClass Class used for constructing the logger.
 * @return A new [KLogger] for the respective class.
 */
@JvmSynthetic
public inline fun <reified T : Any> getLogger(kClass: KClass<T> = T::class): KLoggerDelegate<T> {
    return KLoggerCache.loggerDelegate(kClass.java)
}

/**
 * Constructs a new logger using the provided class.
 *
 * @param clazz Class used for constructing the logger.
 * @return A new [KLogger] for the respective class.
 */
@JvmSynthetic
public inline fun <reified T : Any> getLogger(clazz: Class<T> = T::class.java): KLoggerDelegate<T> {
    return KLoggerCache.loggerDelegate(clazz)
}

/**
 * Constructs a new logger using the provided name.
 *
 * @param name The name of the logger
 * @return A new [KLogger] for the provided name.
 */
@JvmSynthetic
@Suppress("UNCHECKED_CAST")
public fun <T> getLogger(name: String): KLoggerDelegate<T> = KLoggerCache.loggerDelegate(name) as KLoggerDelegate<T>

