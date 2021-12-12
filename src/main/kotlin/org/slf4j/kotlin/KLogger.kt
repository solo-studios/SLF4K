/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2021-2021 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file KLogger.kt is part of SLF4K
 * Last modified on 12-12-2021 04:08 p.m.
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

import org.slf4j.Logger
import kotlin.reflect.KProperty

/**
 * Wrapper around a [Logger] that delegates all calls to the internal logger.
 *
 * Currently empty, but will prevent us from changing the API if we ever have to add anything here.
 *
 * @param delegate [Logger] instance to wrap.
 */
open class KLogger(delegate: Logger) : Logger by delegate {
    /**
     * Operator to allow for property delegation of loggers.
     *
     * This is so you can do
     * ```kotlin
     * val logger by getLogger()
     * ```
     */
    operator fun getValue(thisRef: Any?, property: KProperty<*>): KLogger {
        return this
    }
}
