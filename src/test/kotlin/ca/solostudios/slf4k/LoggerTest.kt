/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2021-2021 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file LoggerTest.kt is part of SLF4K
 * Last modified on 12-12-2021 04:14 p.m.
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
        val logger by getLogger()
        
        assertEquals(LoggerTest::class.qualifiedName, logger.name)
    }
    
    @Test
    fun `test logger name by kclass`() {
        val logger by getLogger(LoggerTest::class)
        
        assertEquals(LoggerTest::class.qualifiedName, logger.name)
    }
    
    @Test
    fun `test logger by class name`() {
        val logger by getLogger(LoggerTest::class.java)
        
        assertEquals(LoggerTest::class.qualifiedName, logger.name)
    }
    
    @Test
    fun `test automatic logger name in lambda`() {
        val lambda = {
            val logger by getLogger()
            
            assertEquals(LoggerTest::class.qualifiedName, logger.name)
        }
        
        lambda()
    }
}
