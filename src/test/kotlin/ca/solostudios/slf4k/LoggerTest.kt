/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2021-2022 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file LoggerTest.kt is part of SLF4K
 * Last modified on 20-11-2022 01:15 p.m.
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

package ca.solostudios.slf4k

import org.slf4j.kotlin.getLogger
import kotlin.test.Test
import kotlin.test.assertEquals

class LoggerTest {
    @Test
    fun `test automatic logger name`() {
        val automaticLogger = AutomaticLoggerName()
    
        assertEquals(AutomaticLoggerName::class.qualifiedName, automaticLogger.logger.name)
    }
    
    @Test
    fun `test logger name by kclass`() {
        val loggerByKClass = LoggerNameByKClass()
        
        assertEquals(LoggerNameByKClass::class.qualifiedName, loggerByKClass.logger.name)
    }
    
    @Test
    fun `test automatic logger name by companion`() {
        assertEquals(AutomaticCompanionLoggerName::class.qualifiedName, AutomaticCompanionLoggerName.logger.name)
    }
    
    @Test
    fun `test logger name by string`() {
        val logger by getLogger("testing")
        
        
        assertEquals("testing", logger.name)
    }
}

class AutomaticCompanionLoggerName {
    companion object {
        val logger by getLogger()
    }
}

class AutomaticLoggerName {
    val logger by getLogger()
}

class LoggerNameByKClass {
    val logger by getLogger(LoggerNameByKClass::class)
}
