/*
 * SLF4K - A Discord bot for the Polyhedral Development discord server
 * Copyright (c) 2021-2021 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file mdc.kt is part of SLF4K
 * Last modified on 22-08-2021 05:41 p.m.
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

/**
 * Constructs a new [MDCCoroutineContext], inheriting the current [mdc].
 *
 * @return A new [CoroutineContext]
 */
fun mdcContext(): MDCCoroutineContext = MDCCoroutineContext()

/**
 * Constructs a new [MDCCoroutineContext] using the provided key-value pair as the MDC.
 *
 * @param pair The key-value pair used as the MDC.
 * @return A new [CoroutineContext]
 */
fun mdcContext(pair: Pair<String, String>): MDCCoroutineContext = mdcContext(mapOf(pair))

/**
 * Constructs a new [MDCCoroutineContext] using the provided key-value pairs as the MDC.
 *
 * @param pairs The key-value pairs to be used as the MDC.
 * @return A new [CoroutineContext]
 */
fun mdcContext(vararg pairs: Pair<String, String>): MDCCoroutineContext = mdcContext(mapOf(*pairs))

/**
 * Constructs a new [MDCCoroutineContext] using the provided map as the MDC.
 *
 * @param mdcMap The map to be used as the MDC.
 * @return A new [CoroutineContext]
 */
fun mdcContext(mdcMap: Map<String, String>): MDCCoroutineContext = MDCCoroutineContext(mdcMap)

/**
 * A wrapper object for SLF4J's [MDC] class.
 *
 * This object is just to make it a bit easier to interact with the MDC map.
 */
@Suppress("ClassName")
object mdc : MutableMap<String, String>,
             AbstractMutableMap<String, String>() {
    
    internal fun asMap(): MutableMap<String, String> = MDC.getCopyOfContextMap() ?: mutableMapOf()
    
    /**
     * Get the current MDC as a [CoroutineContext]
     *
     * @see [MDCCoroutineContext]
     */
    val context: MDCCoroutineContext
        get() = MDCCoroutineContext()
    
    /**
     * Retrieves a value from the current MDC.
     *
     * @param key The key to retrieve
     * @return The string value identified by the key parameter.
     */
    override operator fun get(key: String): String? = MDC.get(key)
    
    operator fun set(key: String, value: String?) {
        if (value != null)
            MDC.put(key, value)
        else
            MDC.remove(key)
    }
    
    override fun containsKey(key: String): Boolean = MDC.get(key) != null
    
    override val entries: MutableSet<MutableMap.MutableEntry<String, String>>
        get() = asMap().entries
    
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

/**
 * A [CoroutineContext] which holds a reference to the MDC.
 *
 * @property contextMap The MDC to be passed to the associated coroutine
 * @constructor Create a new context, with reference to the provided map as MDC or, by default, the current MDC.
 */
class MDCCoroutineContext(private var contextMap: Map<String, String> = mdc.asMap()) : ThreadContextElement<Map<String, String>?> {
    companion object Key : CoroutineContext.Key<MDCCoroutineContext>
    
    override val key: CoroutineContext.Key<*>
        get() = Key
    
    override fun updateThreadContext(context: CoroutineContext): Map<String, String> {
        val oldState: Map<String, String> = mdc.asMap()
        MDC.setContextMap(contextMap)
        return oldState
    }
    
    override fun restoreThreadContext(context: CoroutineContext, oldState: Map<String, String>?) {
        contextMap = mdc.asMap()
        if (oldState == null)
            MDC.clear()
        else
            MDC.setContextMap(oldState)
    }
}