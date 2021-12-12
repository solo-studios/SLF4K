/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2021-2021 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file MDCCoroutineTest.kt is part of SLF4K
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

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.kotlin.mdc
import org.slf4j.kotlin.mdcContext
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MDCCoroutineTest {
    @BeforeEach
    fun clearMdc() {
        mdc.clear()
    }
    
    @Test
    fun testExplicitContext() {
        runBlocking(mdcContext("one" to "two")) {
            assertEquals(1, mdc.size)
            assertEquals("two", mdc["one"])
        }
    }
    
    @Test
    fun testImplicitlyInheritedMdcContext() {
        runBlocking(mdcContext("abcd" to "123")) {
            mdc["test"] = "abcd"
            
            launch { // should inherit {abcd=123,test=abcd}
                println(mdc)
                assertEquals(2, mdc.size)
                assertEquals("123", mdc["abcd"])
                assertEquals("abcd", mdc["test"])
            }
        }
    }
    
    @Test
    fun testInheritedMdcContext() {
        mdc["test"] = "abcd"
        
        runBlocking(mdc.context) { // should inherit {test=abcd}
            println(mdc)
            assertEquals(1, mdc.size)
            assertEquals("abcd", mdc["test"])
        }
    }
    
    @Test
    fun testMdcRemoval() {
        runBlocking(mdc.context) {
            mdc["test"] = "abcd"
            
            launch {
                mdc.clear()
                assertTrue(mdc.isEmpty())
            }
            
            assertEquals(1, mdc.size)
        }
    }
    
    @Test
    fun testMdcPropagation() {
        mdc["testing"] = "abcd"
        
        runBlocking(mdc.context) {
            mdc["one"] = "two"
            launch {
                mdc.clear()
            }
            
            assertEquals(2, mdc.size)
            assertEquals("two", mdc["one"])
            assertEquals("abcd", mdc["testing"])
        }
        
        assertEquals(1, mdc.size)
        assertEquals("abcd", mdc["testing"])
    }
}
