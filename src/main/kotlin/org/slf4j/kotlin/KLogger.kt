/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2021-2022 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file KLogger.kt is part of SLF4K
 * Last modified on 20-11-2022 03:20 p.m.
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
import org.slf4j.Marker
import org.slf4j.event.Level
import org.slf4j.spi.DefaultLoggingEventBuilder
import org.slf4j.spi.LoggingEventBuilder
import org.slf4j.spi.NOPLoggingEventBuilder

/**
 * Wrapper around a [Logger] that delegates all calls to the internal logger.
 *
 * Currently empty, but will prevent us from changing the API if we ever have to add anything here.
 *
 * @param delegate [Logger] instance to wrap.
 */
public class KLogger(private val delegate: Logger) : Logger by delegate {
    /**
     * Return the name of this [Logger] instance.
     * @return name of this logger instance
     */
    override fun getName(): String = delegate.name
    
    /**
     *
     * Make a new [LoggingEventBuilder] instance as appropriate for this logger implementation.
     * This default implementation always returns a new instance of [DefaultLoggingEventBuilder].
     *
     *
     * Note that the [LoggingEventBuilder] should be built for all levels, independently of the level.
     * In other words, this method is an **unconditional** constructor for the [LoggingEventBuilder]
     * appropriate for this logger implementation.
     *
     * @param level desired level for the event builder
     * @return a new [LoggingEventBuilder] instance as appropriate for this logger
     * @since 2.0
     */
    override fun makeLoggingEventBuilder(level: Level?): LoggingEventBuilder = delegate.makeLoggingEventBuilder(level)
    
    /**
     * Make a new [LoggingEventBuilder] instance as appropriate for this logger and the
     * desired [Level] passed as parameter. If this Logger is disabled for the given Level, then
     * a [NOPLoggingEventBuilder] is returned.
     *
     *
     * @param level desired level for the event builder
     * @return a new [LoggingEventBuilder] instance as appropriate for this logger
     * @since 2.0
     */
    override fun atLevel(level: Level?): LoggingEventBuilder = delegate.atLevel(level)
    
    /**
     * Returns whether this Logger is enabled for a given [Level].
     *
     * @param level
     * @return `true` if enabled, `false` otherwise.
     */
    override fun isEnabledForLevel(level: Level?): Boolean = delegate.isEnabledForLevel(level)
    
    /**
     * Is the logger instance enabled for the [Level.TRACE] level?
     *
     * @return `true` if this Logger is enabled for the [Level.TRACE] level,
     * `false` otherwise.
     * @since 1.4
     */
    override fun isTraceEnabled(): Boolean = delegate.isTraceEnabled
    
    /**
     * Similar to [isTraceEnabled] method except that the
     * marker data is also taken into account.
     *
     * @param marker The marker data to take into consideration
     * @return `true` if this Logger is enabled for the [Level.TRACE] level,
     * `false` otherwise.
     *
     * @since 1.4
     */
    override fun isTraceEnabled(marker: Marker?): Boolean = delegate.isTraceEnabled(marker)
    
    /**
     * Log a message with the specific Marker at the [Level.TRACE] level.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message string to be logged
     * @since 1.4
     */
    override fun trace(marker: Marker?, msg: String?): Unit = delegate.trace(marker, msg)
    
    /**
     * Log a message at the [Level.TRACE] level according to the specified format
     * and arguments.
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the [Level.TRACE] level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @since 1.4
     */
    override fun trace(format: String?, arg1: Any?, arg2: Any?): Unit = delegate.trace(format, arg1, arg2)
    
    /**
     * Log a message at the [Level.TRACE] level.
     *
     * @param msg the message string to be logged
     * @since 1.4
     */
    override fun trace(msg: String?): Unit = delegate.trace(msg)
    
    /**
     * Log a message at the [Level.TRACE] level according to the specified format
     * and argument.
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the [Level.TRACE] level.
     *
     * @param format the format string
     * @param arg    the argument
     * @since 1.4
     */
    override fun trace(format: String?, arg: Any?): Unit = delegate.trace(format, arg)
    
    /**
     * This method is similar to [trace]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @since 1.4
     */
    override fun trace(marker: Marker?, format: String?, arg1: Any?, arg2: Any?): Unit = delegate.trace(marker, format, arg1, arg2)
    
    /**
     * This method is similar to [trace] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     * @since 1.4
     */
    override fun trace(marker: Marker?, msg: String?, t: Throwable?): Unit = delegate.trace(marker, msg, t)
    
    /**
     * Log a message at the [Level.TRACE] level according to the specified format
     * and arguments.
     *
     *
     * This form avoids superfluous string concatenation when the logger
     * is disabled for the [Level.TRACE] level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an `Object[]` before invoking the method,
     * even if this logger is disabled for [Level.TRACE]. The variants takingone and two
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     * @since 1.4
     */
    override fun trace(format: String?, vararg arguments: Any?): Unit = delegate.trace(format, arguments)
    
    /**
     * Log an exception (throwable) at the [Level.TRACE] level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     * @since 1.4
     */
    override fun trace(msg: String?, t: Throwable?): Unit = delegate.trace(msg, t)
    
    /**
     * This method is similar to [trace]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker   the marker data specific to this log statement
     * @param format   the format string
     * @param arguments an array of arguments
     * @since 1.4
     */
    override fun trace(marker: Marker?, format: String?, vararg arguments: Any?): Unit = delegate.trace(marker, format, arguments)
    
    /**
     * This method is similar to [trace] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     * @since 1.4
     */
    override fun trace(marker: Marker?, format: String?, arg: Any?): Unit = delegate.trace(marker, format, arg)
    
    /**
     * Entry point for fluent-logging for [Level.TRACE] level.
     *
     * @return LoggingEventBuilder instance as appropriate for level [Level.TRACE]
     * @since 2.0
     */
    override fun atTrace(): LoggingEventBuilder = delegate.atTrace()
    
    /**
     * Similar to [isDebugEnabled] method except that the
     * marker data is also taken into account.
     *
     * @param marker The marker data to take into consideration
     * @return `true` if this Logger is enabled for the [Level.DEBUG] level,
     * `false` otherwise.
     */
    override fun isDebugEnabled(marker: Marker?): Boolean = delegate.isDebugEnabled(marker)
    
    /**
     * Is the logger instance enabled for the [Level.DEBUG] level?
     *
     * @return `true` if this Logger is enabled for the [Level.DEBUG] level,
     * `false` otherwise.
     */
    override fun isDebugEnabled(): Boolean = delegate.isDebugEnabled
    
    /**
     * Log a message at the [Level.DEBUG] level.
     *
     * @param msg the message string to be logged
     */
    override fun debug(msg: String?): Unit = delegate.debug(msg)
    
    /**
     * Log a message at the [Level.DEBUG] level according to the specified format
     * and argument.
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the [Level.DEBUG] level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    override fun debug(format: String?, arg: Any?): Unit = delegate.debug(format, arg)
    
    /**
     * This method is similar to [debug]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    override fun debug(marker: Marker?, format: String?, arg1: Any?, arg2: Any?): Unit = delegate.debug(marker, format, arg1, arg2)
    
    /**
     * Log a message with the specific Marker at the [Level.DEBUG] level.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message string to be logged
     */
    override fun debug(marker: Marker?, msg: String?): Unit = delegate.debug(marker, msg)
    
    /**
     * This method is similar to [debug] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     */
    override fun debug(marker: Marker?, msg: String?, t: Throwable?): Unit = delegate.debug(marker, msg, t)
    
    /**
     * Log an exception (throwable) at the [Level.DEBUG] level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    override fun debug(msg: String?, t: Throwable?): Unit = delegate.debug(msg, t)
    
    /**
     * Log a message at the [Level.DEBUG] level according to the specified format
     * and arguments.
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the [Level.DEBUG] level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    override fun debug(format: String?, arg1: Any?, arg2: Any?): Unit = delegate.debug(format, arg1, arg2)
    
    /**
     * Log a message at the [Level.DEBUG] level according to the specified format
     * and arguments.
     *
     *
     * This form avoids superfluous string concatenation when the logger
     * is disabled for the [Level.DEBUG] level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an `Object[]` before invoking the method,
     * even if this logger is disabled for [Level.DEBUG]. The variants takingone and two
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    override fun debug(format: String?, vararg arguments: Any?): Unit = delegate.debug(format, arguments)
    
    /**
     * This method is similar to [debug]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    override fun debug(marker: Marker?, format: String?, vararg arguments: Any?): Unit = delegate.debug(marker, format, arguments)
    
    /**
     * This method is similar to [debug] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    override fun debug(marker: Marker?, format: String?, arg: Any?): Unit = delegate.debug(marker, format, arg)
    
    /**
     * Entry point for fluent-logging for [Level.DEBUG] level.
     *
     * @return LoggingEventBuilder instance as appropriate for level [Level.DEBUG]
     * @since 2.0
     */
    override fun atDebug(): LoggingEventBuilder = delegate.atDebug()
    
    /**
     * Similar to [isInfoEnabled] method except that the marker
     * data is also taken into consideration.
     *
     * @param marker The marker data to take into consideration
     * @return `true` if this Logger is enabled for the [Level.INFO] level,
     * `false` otherwise.
     */
    override fun isInfoEnabled(marker: Marker?): Boolean = delegate.isInfoEnabled(marker)
    
    /**
     * Is the logger instance enabled for the [Level.INFO] level?
     *
     * @return `true` if this Logger is enabled for the [Level.INFO] level,
     * `false` otherwise.
     */
    override fun isInfoEnabled(): Boolean = delegate.isInfoEnabled
    
    /**
     * Log a message with the specific Marker at the [Level.INFO] level.
     *
     * @param marker The marker specific to this log statement
     * @param msg    the message string to be logged
     */
    override fun info(marker: Marker?, msg: String?): Unit = delegate.info(marker, msg)
    
    /**
     * This method is similar to [info] method
     * except that the marker data is also taken into consideration.
     *
     * @param marker the marker data for this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     */
    override fun info(marker: Marker?, msg: String?, t: Throwable?): Unit = delegate.info(marker, msg, t)
    
    /**
     * Log a message at the [Level.INFO] level according to the specified format
     * and argument.
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the [Level.INFO] level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    override fun info(format: String?, arg: Any?): Unit = delegate.info(format, arg)
    
    /**
     * Log an exception (throwable) at the [Level.INFO] level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    override fun info(msg: String?, t: Throwable?): Unit = delegate.info(msg, t)
    
    /**
     * This method is similar to [info] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    override fun info(marker: Marker?, format: String?, arg: Any?): Unit = delegate.info(marker, format, arg)
    
    /**
     * This method is similar to [info]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    override fun info(marker: Marker?, format: String?, arg1: Any?, arg2: Any?): Unit = delegate.info(marker, format, arg1, arg2)
    
    /**
     * Log a message at the [Level.INFO] level.
     *
     * @param msg the message string to be logged
     */
    override fun info(msg: String?): Unit = delegate.info(msg)
    
    /**
     * Log a message at the [Level.INFO] level according to the specified format
     * and arguments.
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the [Level.INFO] level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    override fun info(format: String?, arg1: Any?, arg2: Any?): Unit = delegate.info(format, arg1, arg2)
    
    /**
     * Log a message at the [Level.INFO] level according to the specified format
     * and arguments.
     *
     *
     * This form avoids superfluous string concatenation when the logger
     * is disabled for the [Level.INFO] level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an `Object[]` before invoking the method,
     * even if this logger is disabled for [Level.INFO]. The variants takingone and two
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    override fun info(format: String?, vararg arguments: Any?): Unit = delegate.info(format, arguments)
    
    /**
     * This method is similar to [info]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    override fun info(marker: Marker?, format: String?, vararg arguments: Any?): Unit = delegate.info(marker, format, arguments)
    
    /**
     * Entry point for fluent-logging for [Level.INFO] level.
     *
     * @return LoggingEventBuilder instance as appropriate for level [Level.INFO]
     * @since 2.0
     */
    override fun atInfo(): LoggingEventBuilder = delegate.atInfo()
    
    /**
     * Is the logger instance enabled for the [Level.WARN] level?
     *
     * @return `true` if this Logger is enabled for the [Level.WARN] level,
     * `false` otherwise.
     */
    override fun isWarnEnabled(): Boolean = delegate.isWarnEnabled
    
    /**
     * Similar to [isWarnEnabled] method except that the marker
     * data is also taken into consideration.
     *
     * @param marker The marker data to take into consideration
     * @return `true` if this Logger is enabled for the [Level.WARN] level,
     * `false` otherwise.
     */
    override fun isWarnEnabled(marker: Marker?): Boolean = delegate.isWarnEnabled(marker)
    
    /**
     * Log a message at the [Level.WARN] level according to the specified format
     * and argument.
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the [Level.WARN] level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    override fun warn(format: String?, arg: Any?): Unit = delegate.warn(format, arg)
    
    /**
     * Log an exception (throwable) at the [Level.WARN] level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    override fun warn(msg: String?, t: Throwable?): Unit = delegate.warn(msg, t)
    
    /**
     * Log a message at the [Level.WARN] level according to the specified format
     * and arguments.
     *
     *
     * This form avoids superfluous string concatenation when the logger
     * is disabled for the [Level.WARN] level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an `Object[]` before invoking the method,
     * even if this logger is disabled for [Level.WARN]. The variants takingone and two
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    override fun warn(format: String?, vararg arguments: Any?): Unit = delegate.warn(format, arguments)
    
    /**
     * Log a message at the [Level.WARN] level.
     *
     * @param msg the message string to be logged
     */
    override fun warn(msg: String?): Unit = delegate.warn(msg)
    
    /**
     * Log a message with the specific Marker at the [Level.WARN] level.
     *
     * @param marker The marker specific to this log statement
     * @param msg    the message string to be logged
     */
    override fun warn(marker: Marker?, msg: String?): Unit = delegate.warn(marker, msg)
    
    /**
     * Log a message at the [Level.WARN] level according to the specified format
     * and arguments.
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the [Level.WARN] level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    override fun warn(format: String?, arg1: Any?, arg2: Any?): Unit = delegate.warn(format, arg1, arg2)
    
    /**
     * This method is similar to [warn]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    override fun warn(marker: Marker?, format: String?, arg1: Any?, arg2: Any?): Unit = delegate.warn(marker, format, arg1, arg2)
    
    /**
     * This method is similar to [warn] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    override fun warn(marker: Marker?, format: String?, arg: Any?): Unit = delegate.warn(marker, format, arg)
    
    /**
     * This method is similar to [warn]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    override fun warn(marker: Marker?, format: String?, vararg arguments: Any?): Unit = delegate.warn(marker, format, arguments)
    
    /**
     * This method is similar to [warn] method
     * except that the marker data is also taken into consideration.
     *
     * @param marker the marker data for this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     */
    override fun warn(marker: Marker?, msg: String?, t: Throwable?): Unit = delegate.warn(marker, msg, t)
    
    /**
     * Entry point for fluent-logging for [Level.WARN] level.
     *
     * @return LoggingEventBuilder instance as appropriate for level [Level.WARN]
     * @since 2.0
     */
    override fun atWarn(): LoggingEventBuilder = delegate.atWarn()
    
    /**
     * Is the logger instance enabled for the [Level.ERROR] level?
     *
     * @return `true` if this Logger is enabled for the [Level.ERROR] level,
     * `false` otherwise.
     */
    override fun isErrorEnabled(): Boolean = delegate.isErrorEnabled
    
    /**
     * Similar to [isErrorEnabled] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker The marker data to take into consideration
     * @return `true` if this Logger is enabled for the [Level.ERROR] level,
     * `false` otherwise.
     */
    override fun isErrorEnabled(marker: Marker?): Boolean = delegate.isErrorEnabled(marker)
    
    /**
     * Log a message at the [Level.ERROR] level according to the specified format
     * and arguments.
     *
     *
     * This form avoids superfluous string concatenation when the logger
     * is disabled for the [Level.ERROR] level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an `Object[]` before invoking the method,
     * even if this logger is disabled for [Level.ERROR]. The variants takingone and two
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    override fun error(format: String?, vararg arguments: Any?): Unit = delegate.error(format, arguments)
    
    /**
     * This method is similar to [error] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    override fun error(marker: Marker?, format: String?, arg: Any?): Unit = delegate.error(marker, format, arg)
    
    /**
     * This method is similar to [error]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     */
    override fun error(marker: Marker?, msg: String?, t: Throwable?): Unit = delegate.error(marker, msg, t)
    
    /**
     * Log a message at the [Level.ERROR] level according to the specified format
     * and argument.
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the [Level.ERROR] level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    override fun error(format: String?, arg: Any?): Unit = delegate.error(format, arg)
    
    /**
     * This method is similar to [error]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    override fun error(marker: Marker?, format: String?, vararg arguments: Any?): Unit = delegate.error(marker, format, arguments)
    
    /**
     * This method is similar to [error]
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    override fun error(marker: Marker?, format: String?, arg1: Any?, arg2: Any?): Unit = delegate.error(marker, format, arg1, arg2)
    
    /**
     * Log an exception (throwable) at the [Level.ERROR] level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    override fun error(msg: String?, t: Throwable?): Unit = delegate.error(msg, t)
    
    /**
     * Log a message at the [Level.ERROR] level.
     *
     * @param msg the message string to be logged
     */
    override fun error(msg: String?): Unit = delegate.error(msg)
    
    /**
     * Log a message with the specific Marker at the [Level.ERROR] level.
     *
     * @param marker The marker specific to this log statement
     * @param msg    the message string to be logged
     */
    override fun error(marker: Marker?, msg: String?): Unit = delegate.error(marker, msg)
    
    /**
     * Log a message at the [Level.ERROR] level according to the specified format
     * and arguments.
     *
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the [Level.ERROR] level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    override fun error(format: String?, arg1: Any?, arg2: Any?): Unit = delegate.error(format, arg1, arg2)
    
    /**
     * Entry point for fluent-logging for [Level.ERROR] level.
     *
     * @return LoggingEventBuilder instance as appropriate for level [Level.ERROR]
     * @since 2.0
     */
    override fun atError(): LoggingEventBuilder = delegate.atError()
}
