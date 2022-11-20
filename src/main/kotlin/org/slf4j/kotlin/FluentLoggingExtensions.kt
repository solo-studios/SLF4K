/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2022-2022 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file FluentLoggingExtensions.kt is part of SLF4K
 * Last modified on 20-11-2022 01:55 p.m.
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

import org.slf4j.spi.LoggingEventBuilder as SLF4JLoggingEventBuilder

/**
 * Log an event at the TRACE level using the fluent logging api.
 *
 * @param builder the builder to create the logging event
 */
@JvmSynthetic
public inline fun KLogger.atTrace(crossinline builder: LoggingEventBuilder.() -> Unit) {
    if (isTraceEnabled) {
        val slf4jEventBuilder = atTrace()
        LoggingEventBuilder()
                .apply(builder)
                .applyTo(slf4jEventBuilder)
        
        slf4jEventBuilder.log()
    }
}

/**
 * Log an event at the DEBUG level using the fluent logging api.
 *
 * @param builder the builder to create the logging event
 */
@JvmSynthetic
public inline fun KLogger.atDebug(crossinline builder: LoggingEventBuilder.() -> Unit) {
    if (isDebugEnabled) {
        val slf4jEventBuilder = atDebug()
        LoggingEventBuilder()
                .apply(builder)
                .applyTo(slf4jEventBuilder)
        
        slf4jEventBuilder.log()
    }
}

/**
 * Log an event at the INFO level using the fluent logging api.
 *
 * @param builder the builder to create the logging event
 */
@JvmSynthetic
public inline fun KLogger.atInfo(crossinline builder: LoggingEventBuilder.() -> Unit) {
    if (isInfoEnabled) {
        val slf4jEventBuilder = atInfo()
        LoggingEventBuilder()
                .apply(builder)
                .applyTo(slf4jEventBuilder)
        
        slf4jEventBuilder.log()
    }
}

/**
 * Log an event at the WARN level using the fluent logging api.
 *
 * @param builder the builder to create the logging event
 */
@JvmSynthetic
public inline fun KLogger.atWarn(crossinline builder: LoggingEventBuilder.() -> Unit) {
    if (isWarnEnabled) {
        val slf4jEventBuilder = atWarn()
        LoggingEventBuilder()
                .apply(builder)
                .applyTo(slf4jEventBuilder)
        
        slf4jEventBuilder.log()
    }
}

/**
 * Log an event at the ERROR level using the fluent logging api.
 *
 * @param builder the builder to create the logging event
 */
@JvmSynthetic
public inline fun KLogger.atError(crossinline builder: LoggingEventBuilder.() -> Unit) {
    if (isErrorEnabled) {
        val slf4jEventBuilder = atError()
        LoggingEventBuilder()
                .apply(builder)
                .applyTo(slf4jEventBuilder)
        
        slf4jEventBuilder.log()
    }
}

@PublishedApi
internal fun LoggingEventBuilder.applyTo(slf4jEventBuilder: SLF4JLoggingEventBuilder) {
    cause?.also {
        slf4jEventBuilder.setCause(it)
    }
    
    markers?.forEach { marker ->
        slf4jEventBuilder.addMarker(marker)
    }
    
    arguments?.forEach {
        slf4jEventBuilder.addArgument(it)
    }
    
    keyValues?.forEach { (key, value) ->
        slf4jEventBuilder.addKeyValue(key, value)
    }
    
    message?.also {
        slf4jEventBuilder.setMessage(it)
    }
}

/**
 * A fluent logging event builder that is more idiomatic for Kotlin.
 *
 * @see SLF4JLoggingEventBuilder
 */
public class LoggingEventBuilder {
    /**
     * The cause for the logging event being built.
     *
     * @see SLF4JLoggingEventBuilder.setCause
     */
    public var cause: Throwable? = null
    
    /**
     * A list of [marker][KMarker]s to be added to the event being built.
     *
     * @see SLF4JLoggingEventBuilder.addMarker
     */
    public var markers: List<KMarker>? = null
    
    /**
     * A list of arguments to be added to the event being built.
     *
     * @see SLF4JLoggingEventBuilder.addArgument
     */
    public var arguments: List<*>? = null
    
    /**
     * A list of [KeyValuePair][org.slf4j.event.KeyValuePair]s to be added to the event being built.
     *
     * @see SLF4JLoggingEventBuilder.addKeyValue
     */
    public var keyValues: List<Pair<String, *>>? = null
    
    /**
     * The message for the logging event being build
     *
     * @see SLF4JLoggingEventBuilder.setMessage
     */
    public var message: String? = null
}
