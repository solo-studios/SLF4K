/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2022-2022 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file SimpleServiceMDCProvider.kt is part of SLF4K
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

package org.slf4j.impl

import ca.solostudios.kspservice.annotation.Service
import org.slf4j.ILoggerFactory
import org.slf4j.IMarkerFactory
import org.slf4j.helpers.BasicMDCAdapter
import org.slf4j.helpers.BasicMarkerFactory
import org.slf4j.simple.SimpleLoggerFactory
import org.slf4j.spi.MDCAdapter
import org.slf4j.spi.SLF4JServiceProvider

@Service(SLF4JServiceProvider::class)
class SimpleServiceMDCProvider : SLF4JServiceProvider {
    private lateinit var loggerFactory: ILoggerFactory
    private lateinit var markerFactory: IMarkerFactory
    private lateinit var mdcAdapter: MDCAdapter
    
    override fun getLoggerFactory(): ILoggerFactory {
        return loggerFactory
    }
    
    override fun getMarkerFactory(): IMarkerFactory {
        return markerFactory
    }
    
    override fun getMDCAdapter(): MDCAdapter {
        return mdcAdapter
    }
    
    override fun getRequestedApiVersion(): String = REQUESTED_API_VERSION
    
    override fun initialize() {
        loggerFactory = SimpleLoggerFactory()
        markerFactory = BasicMarkerFactory()
        mdcAdapter = BasicMDCAdapter()
    }
    
    companion object {
        /**
         * Declare the version of the SLF4J API this implementation is compiled against.
         * The value of this field is modified with each major release.
         */
        // to avoid constant folding by the compiler, this field must *not* be final
        var REQUESTED_API_VERSION = "2.0.99" // !final
    }
}
