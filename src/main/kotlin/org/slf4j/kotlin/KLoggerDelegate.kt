/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2022-2023 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file KLoggerDelegate.kt is part of SLF4K
 * Last modified on 19-01-2023 02:15 p.m.
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

import org.slf4j.LoggerFactory
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A delegate that allows [KLogger] to be instantiated lazily.
 *
 * @param T The type of the logger
 * @property loggerNameProvider The provider for the name of the logger
 */
public class KLoggerDelegate<T>(
        private val loggerNameProvider: () -> String
) : ReadOnlyProperty<T?, KLogger> {
    private val lazyLogger: KLogger by lazy {
        KLogger(LoggerFactory.getLogger(loggerNameProvider()))
    }
    
    /**
     * Lazily retrieves the logger for this delegate.
     */
    override fun getValue(thisRef: T?, property: KProperty<*>): KLogger {
        return lazyLogger
    }
}
