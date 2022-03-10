# SLF4K

[![Build Status](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fci.solo-studios.ca%2Fjob%2Fsolo-studios%2Fjob%2FSLF4K%2F&style=for-the-badge)](https://ci.solo-studios.ca/job/solo-studios/job/Strata/)
[![MIT license](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)](LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/ca.solo-studios/slf4k.svg?style=for-the-badge&label=Maven%20Central)](https://search.maven.org/search?q=g:ca.solo-studios%20a:slf4k)
[![Pure Kotlin](https://img.shields.io/badge/100%25-kotlin-blue.svg?style=for-the-badge)](https://kotlinlang.org/)
[![Discord Server](https://img.shields.io/discord/871114669761372221?color=7389D8&label=Discord&logo=discord&logoColor=8fa3ff&style=for-the-badge)](https://discord.solo-studios.ca)

*Simple Logging Facade for Kotlin is a set of Kotlin extension for SLF4J.*

## Features

- Use kotlin features to cleanly get the appropriate logger for your class
- Clean up kotlin class names to make logging easy to understand
- Easy and simple MDC usage
- MDC support for Coroutines

## Including

You can include SLF4K in your project by adding the following:

### Maven

```xml
<dependency>
  <groupId>ca.solo-studios</groupId>
  <artifactId>slf4k</artifactId>
  <version>0.4.6</version>
</dependency>
```

### Gradle Groovy DSL

```groovy
implementation 'ca.solo-studios:slf4k:0.4.6'
```

### Gradle Kotlin DSL

```kotlin
implementation("ca.solo-studios:slf4k:0.4.6")
```

## Examples

### Getting a Logger

How to get a logger:

```kotlin
val logger by getLogger()
```

or

```kotlin
val logger = getLogger()
```

### Logging

SLF4K uses lazy log message evaluation to avoid expensive logging messages when they're not needed.

When you write the following:

```kotlin
logger.info { "expensive message: $object" }
```

this code gets transformed at compiled time and inlined into:

```kotlin
if (logger.info)
    logger.info("expensive message: $object")
```

This way, the expensive message is only evaluated if the info log level is enabled.

Extensions have also been provided for every combination of level and argument. So, you can log errors as needed:

```kotlin
logger.warn(exception) { "message" }
```

### Using markers

Markers can also be really easily instantiated, as follows:

```kotlin
val myMarker by getMarker("MY_MARKER")
```

after which, they can be used normally.

### Using MDC

MDC can also be used easily in SLF4K.

The MDC map can easily be manipulated as such:

```kotlin
mdc["myKey"] = "myValue"

val myValue = mdc["myKey"]

mdc["myKey"] = null // setting it to null can be used to remove it
```

### MDC with Coroutines

Though, one of the problems with MDC is that if you are using it
with [kotlinx coroutines](https://github.com/Kotlin/kotlinx.coroutines)*, then the MDC values will not carry between
threads.

```kotlin
mdc["myKey"] = "myValue"

runBlocking {
    val myValue = mdc["myKey"]
    // myValue == null
    // MDC is *not* propagated between each thread.
}
```

So, to solve this, you can use the current MDC as a coroutine context:

```kotlin
mdc["myKey"] = "myValue"

runBlocking(mdc.context) {
    val myValue = mdc["myKey"]
    // myValue == "myValue"
    // MDC is now propagated through the coroutine context
}
```

The MDC is also copied into nested coroutines:

```kotlin
mdc["myKey"] = "myValue"

runBlocking(mdc.context) {
    mdc["myOtherKey"] = "myOtherValue"
    launch {
        val myValue = mdc["myKey"]
        val myOtherValue = mdc["myOtherKey"]
        // myValue == "myValue"
        // myOtherValue == "myOtherValue"
        // MDC caries into children coroutines
    }
}
```

You can also create a new coroutine with a new MDC:

```kotlin
runBlocking(mdcContext("myKey" to "myValue") {
    val myValue = mdc["myKey"]
    // myValue == "myValue"
}
```

But be warned:\
Any changes to the MDC in a child coroutine will *not* affect the parent coroutine:

```kotlin
mdc["myKey"] = "myValue"
runBlocking(mdc.context) {
    mdc["myOtherKey"] = "myOtherValue"
    
    launch {
        mdc.clear()
        
        val myOtherValue = mdc["myOtherKey"]
        val myValue = mdc["myKey"]
        // myOtherValue == null
        // myValue == null
    }
    
    val myOtherValue = mdc["myOtherKey"]
    val myValue = mdc["myKey"]
    // myOtherValue == "myOtherValue"
    // myValue == "myValue"
}

val myOtherValue = mdc["myOtherKey"]
val myValue = mdc["myKey"]
// myOtherValue == null
// myValue == "myValue"
```

*You must include kotlinx.coroutines in your build in addition to SLF4K for this to work.
