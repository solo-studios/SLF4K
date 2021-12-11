/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2021-2021 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file logger.kt is part of SLF4K
 * Last modified on 11-12-2021 06:21 p.m.
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
@file:Suppress("NOTHING_TO_INLINE", "unused")

package org.slf4j.kotlin

import org.slf4j.LoggerFactory
import java.lang.invoke.MethodHandles
import kotlin.reflect.KClass

/**
 * Lazily constructs a new logger using the provided class.
 *
 * @return A new [Lazy] instantiated [KLogger] for the invoking class.
 */
inline fun Any.getLazyLogger(): Lazy<KLogger> = lazy { this.getLogger() }

/**
 * Lazily constructs a new logger using the provided class.
 *
 * @param clazz Class used for constructing the logger.
 * @return A new [Lazy] instantiated [KLogger] for the respective class.
 */
@JvmSynthetic
inline fun getLazyLogger(clazz: KClass<*>): Lazy<KLogger> = lazy { getLogger(clazz) }

/**
 * Lazily constructs a new logger using the provided class.
 *
 * @param clazz Class used for constructing the logger.
 * @return A new [Lazy] instantiated [KLogger] for the respective class.
 */
@JvmSynthetic
inline fun getLazyLogger(clazz: Class<*>): Lazy<KLogger> = lazy { getLogger(clazz) }

/**
 * Lazily constructs a new logger using the provided name.
 *
 * @param name The name of the logger
 * @return A new [Lazy] instantiated [KLogger] for the provided name.
 */
@JvmSynthetic
inline fun getLazyLogger(name: String): Lazy<KLogger> = lazy { getLogger(name) }

/**
 * Constructs a new top-level logger using [MethodHandles].
 *
 * Any classes attempting to get a logger should, by default, using the faster [getLogger].
 *
 * @return A new [Lazy] instantiated [KLogger] for the calling class.
 */
@JvmSynthetic
inline fun <reified T> getLazyLogger(): Lazy<KLogger> = lazy {
    getLogger(T::class)
}

/**
 * Constructs a new logger for the invoking class.
 *
 * @return A new [KLogger] for the invoking class.
 */
inline fun <reified T> T.getLogger(): KLogger = getLogger(T::class) // using reified generic magic is faster than MethodHandles

/**
 * Constructs a new logger using the provided class.
 *
 * @param clazz Class used for constructing the logger.
 * @return A new [KLogger] for the respective class.
 */
@JvmSynthetic
inline fun getLogger(clazz: KClass<*>): KLogger = getLogger(clazz.java)

/**
 * Constructs a new logger using the provided class.
 *
 * @param clazz Class used for constructing the logger.
 * @return A new [KLogger] for the respective class.
 */
@JvmSynthetic
inline fun getLogger(clazz: Class<*>): KLogger {
    val name = clazz.name
    val sliced = name.substringBefore("$") // remove all the bullshit like MainKt$main$$inlined$readValue$1
    val trueName = when { // top level files will have "Kt" added. Remove that. eg. MainKt -> Main.
        sliced.endsWith("Kt") -> name.substringBeforeLast("Kt")
        else                  -> name
    }
    
    return KLogger(LoggerFactory.getLogger(trueName))
}

/**
 * Constructs a new logger using the provided name.
 *
 * @param name The name of the logger
 * @return A new [KLogger] for the provided name.
 */
@JvmSynthetic
inline fun getLogger(name: String): KLogger = KLogger(LoggerFactory.getLogger(name))

/**
 * Constructs a new top-level logger using [MethodHandles].
 *
 * Any classes attempting to get a logger should, by default, using the faster [getLogger].
 *
 * @return A new [KLogger] for the calling class.
 */
@JvmSynthetic
@Deprecated(
    message = "You should use  the version which is reified instead, to avoid the cost of expensive stack trace analysis.",
    replaceWith = ReplaceWith("getLogger()", "org.slf4j.kotlin.getLogger"),
    level = DeprecationLevel.HIDDEN,
)
inline fun getLogger(): KLogger = getLogger(MethodHandles.lookup().lookupClass())
