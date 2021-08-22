/*
 * SLF4K - A Discord bot for the Polyhedral Development discord server
 * Copyright (c) 2021-2021 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file mdc.kt is part of SLF4K
 * Last modified on 21-08-2021 07:42 p.m.
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

import kotlinx.coroutines.ThreadContextElement
import org.slf4j.MDC
import kotlin.coroutines.CoroutineContext

fun mdcContext() = CoroutineMDCContext()

fun mdcContext(pair: Pair<String, String>) = mdcContext(mapOf(pair))

fun mdcContext(vararg pairs: Pair<String, String>) = mdcContext(mapOf(*pairs))

fun mdcContext(mdcMap: Map<String, String>) = CoroutineMDCContext(mdcMap)

@Suppress("ClassName")
object mdc : MutableMap<String, String>,
             AbstractMutableMap<String, String>() {
    
    internal fun getMap(): MutableMap<String, String> = MDC.getCopyOfContextMap() ?: mutableMapOf()
    
    val context: CoroutineMDCContext
        get() = CoroutineMDCContext()
    
    override operator fun get(key: String): String = MDC.get(key)
    
    operator fun set(key: String, value: String?) {
        if (value != null)
            MDC.put(key, value)
        else
            MDC.remove(key)
    }
    
    override fun containsKey(key: String): Boolean = MDC.get(key) != null
    
    override val entries: MutableSet<MutableMap.MutableEntry<String, String>>
        get() = getMap().entries
    
    override fun clear() {
        MDC.clear()
    }
    
    override fun put(key: String, value: String): String? {
        val oldValue = MDC.get(key)
        MDC.put(key, value)
        return oldValue
    }
    
    override fun remove(key: String): String? {
        val oldValue = MDC.get(key)
        MDC.remove(key)
        return oldValue
    }
}


class CoroutineMDCContext(private var contextMap: Map<String, String> = mdc.getMap()) : ThreadContextElement<Map<String, String>?> {
    companion object Key : CoroutineContext.Key<CoroutineMDCContext>
    
    override val key: CoroutineContext.Key<*>
        get() = Key
    
    override fun updateThreadContext(context: CoroutineContext): Map<String, String> {
        val oldState: Map<String, String> = mdc.getMap()
        MDC.setContextMap(contextMap)
        return oldState
    }
    
    override fun restoreThreadContext(context: CoroutineContext, oldState: Map<String, String>?) {
        contextMap = mdc.getMap()
        if (oldState == null)
            MDC.clear()
        else
            MDC.setContextMap(oldState)
    }
}