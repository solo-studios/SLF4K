/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2021-2022 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file KMarker.kt is part of SLF4K
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

import org.slf4j.Marker

/**
 * Wrapper around a [Marker] that delegates all calls to the internal logger.
 *
 * Currently empty, but will prevent us from changing the API if we ever have to add anything here.
 *
 * @param delegate [Marker] instance to wrap.
 */
public class KMarker(private val delegate: Marker) : Marker by delegate {
    /**
     * Get the name of this Marker.
     *
     * @return name of marker
     */
    override fun getName(): String = delegate.name
    
    /**
     * Add a reference to another Marker.
     *
     *
     * Note that the fluent API allows adding multiple markers to a logging statement.
     * It is often preferable to use multiple markers instead of nested markers.
     *
     *
     * @param reference a reference to another marker
     * @throws IllegalArgumentException if [reference] is null
     */
    override fun add(reference: Marker): Unit = delegate.add(reference)
    
    /**
     * Remove a marker reference.
     *
     * @param reference the marker reference to remove
     * @return `true` if reference could be found and removed, `false` otherwise.
     */
    override fun remove(reference: Marker): Boolean = delegate.remove(reference)
    
    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java", replaceWith = ReplaceWith("hasReferences()"))
    override fun hasChildren(): Boolean = delegate.hasChildren()
    
    /**
     * Does this marker have any references?
     *
     * @return `true` if this marker has one or more references, `false` otherwise.
     */
    override fun hasReferences(): Boolean = delegate.hasReferences()
    
    /**
     * Returns an Iterator which can be used to iterate over the references of this
     * marker. An empty iterator is returned when this marker has no references.
     *
     * @return Iterator over the references of this marker
     */
    override fun iterator(): MutableIterator<Marker> = delegate.iterator()
    
    /**
     * Does this marker contain a reference to the [other] marker? Marker A is defined
     * to contain marker B, if A == B or if B is referenced by A, or if B is referenced
     * by any one of A's references (recursively).
     *
     * @param other The marker to test for inclusion.
     * @throws IllegalArgumentException if [other] is null
     * @return Whether this marker contains the other marker.
     */
    override fun contains(other: Marker): Boolean = delegate.contains(other)
    
    /**
     * Does this marker contain the marker named [name]?
     *
     * If [name] is null the returned value is always `false`.
     *
     * @param name The marker name to test for inclusion.
     * @return Whether this marker contains the other marker.
     */
    override fun contains(name: String?): Boolean = delegate.contains(name)
    
    private companion object {
        const val serialVersionUID: Long = -5446870957752841511L
    }
}
