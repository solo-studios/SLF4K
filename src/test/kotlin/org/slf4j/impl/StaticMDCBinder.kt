/*
 * SLF4K - A Discord bot for the Polyhedral Development discord server
 * Copyright (c) 2021-2021 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file StaticMDCBinder.kt is part of SLF4K
 * Last modified on 22-08-2021 07:20 p.m.
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

package org.slf4j.impl

import org.slf4j.helpers.BasicMDCAdapter
import org.slf4j.spi.MDCAdapter

/**
 * It overrides [org.slf4j.impl.StaticMDCBinder] (which binds [NOPMDCAdapter]) to bind [BasicMDCAdapter].
 */
class StaticMDCBinder private constructor() {
    
    companion object {
        @JvmStatic
        val SINGLETON = StaticMDCBinder()
        
        @JvmStatic
        fun getSingleton(): StaticMDCBinder {
            return SINGLETON
        }
    }
    
    fun getMDCA(): MDCAdapter {
        return BasicMDCAdapter()
    }
    
    fun getMDCAdapterClassStr(): String {
        return BasicMDCAdapter::class.java.name
    }
}
