/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2021-2022 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file TopLevelLogger.kt is part of SLF4K
 * Last modified on 19-11-2022 02:16 p.m.
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

package org.slf4j.kotlin.toplevel

import org.slf4j.kotlin.KLogger
import org.slf4j.kotlin.getLogger
import java.lang.invoke.MethodHandles

/**
 * Constructs a new top-level logger using [MethodHandles].
 *
 * Any classes attempting to get a logger should, by default, using the faster [getLogger].
 *
 * @return A new [Lazy] instantiated [KLogger] for the calling class.
 */
@JvmSynthetic
inline fun getLazyLogger(): Lazy<KLogger> = lazy {
    getLogger(MethodHandles.lookup().lookupClass())
}

/**
 * Constructs a new top-level logger using [MethodHandles].
 *
 * Any classes attempting to get a logger should, by default, using the faster [getLogger].
 *
 * @return A new [KLogger] for the calling class.
 */
@JvmSynthetic
inline fun getLogger(): KLogger = getLogger(MethodHandles.lookup().lookupClass())

