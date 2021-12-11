/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2021-2021 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file MDCTest.kt is part of SLF4K
 * Last modified on 06-09-2021 02:38 p.m.
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

import org.junit.jupiter.api.Test
import org.slf4j.MDC
import org.slf4j.kotlin.mdc

import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class MDCTest {
    
    @Test
    fun testMdcPut() {
        mdc["test"] = "abcd"
        
        assertNotNull(MDC.get("test"))
        assertEquals("abcd", MDC.get("test"))
    }
    
    @Test
    fun testMdcGet() {
        MDC.put("test", "abcd")
        
        assertEquals("abcd", mdc["test"])
    }
    
    @Test
    fun testMdcRemove() {
        MDC.put("test", "abcd")
        mdc["test"] = null
        
        assertNull(MDC.get("test"))
        
        MDC.put("test", "abcd")
        mdc.remove("test")
        
        assertNull(MDC.get("test"))
    }
}
