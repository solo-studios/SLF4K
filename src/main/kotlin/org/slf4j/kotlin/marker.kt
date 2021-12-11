/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2021-2021 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file marker.kt is part of SLF4K
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
@file:Suppress("NOTHING_TO_INLINE", "unused")

package org.slf4j.kotlin

import org.slf4j.MarkerFactory

/**
 * Lazily constructs a new marker using the provided name.
 *
 * @param name The name of the marker
 * @return A [Lazy] instantiated [KMarker] for the provided name.
 */
@JvmSynthetic
inline fun getLazyMarker(name: String): Lazy<KMarker> = lazy { getMarker(name) }

/**
 * Constructs a new *detached* marker using the provided name.
 *
 * A detached marker is a marker which is *not* cached after creation.
 *
 * @param name The name of the marker
 * @return A [KMarker] with the provided name.
 */
@JvmSynthetic
inline fun getLazyDetachedMarker(name: String): Lazy<KMarker> = lazy { getDetachedMarker(name) }

/**
 * Constructs a new marker using the provided name.
 *
 * @param name The name of the marker
 * @return A [KMarker] with the provided name.
 */
@JvmSynthetic
inline fun getMarker(name: String): KMarker = KMarker(MarkerFactory.getMarker(name))

/**
 * Constructs a new *detached* marker using the provided name.
 *
 * A detached marker is a marker which is *not* cached after creation.
 *
 * @param name The name of the marker
 * @return A *new* [KMarker] with the provided name.
 */
@JvmSynthetic
inline fun getDetachedMarker(name: String): KMarker = KMarker(MarkerFactory.getDetachedMarker(name))
