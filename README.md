# SLF4K

[![Build Status](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fci.solo-studios.ca%2Fjob%2Fsolo-studios%2Fjob%2FSLF4K%2F&style=for-the-badge)](https://ci.solo-studios.ca/job/solo-studios/job/SLF4K/)
[![MIT license](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)](LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/ca.solo-studios/slf4k.svg?style=for-the-badge&label=Maven%20Central)](https://search.maven.org/search?q=g:ca.solo-studios%20a:slf4k)
[![Pure Kotlin](https://img.shields.io/badge/100%25-kotlin-blue.svg?style=for-the-badge)](https://kotlinlang.org/)
[![Discord Server](https://img.shields.io/discord/871114669761372221?color=7389D8&label=Discord&logo=discord&logoColor=8fa3ff&style=for-the-badge)](https://discord.solo-studios.ca)

*Simple Logging Facade for Kotlin is a set of Kotlin extension for SLF4J.*

## Features

- Use kotlin features to cleanly get the appropriate logger for your class
- Clean up kotlin class names to make logging easy to understand
- Typed logger delegates: a logger instantiated using a class reference is only valid in that class
- Lazy logger instantiation & logger cache

## Including

You can include SLF4K in your project by adding the following:

### Maven

```xml
<dependency>
  <groupId>ca.solo-studios</groupId>
  <artifactId>slf4k</artifactId>
  <version>0.5.3</version>
</dependency>
```

### Gradle Groovy DSL

```groovy
implementation 'ca.solo-studios:slf4k:0.5.3'
```

### Gradle Kotlin DSL

```kotlin
implementation("ca.solo-studios:slf4k:0.5.3")
```

## SLF4J 2.0.0

The current version of SLF4K should be compatible with all versions that are greater than 2.0.0.
However, it should still work with versions lower than 2.0.0, so long as you don't use
the [Fluent Logging](#fluent-logging) extensions.

## Examples

### Getting a Logger

How to get a logger:

#### Top Level

```kotlin
import org.slf4j.kotlin.toplevel.getLogger


val logger by getLogger()
```

#### Class

```kotlin
import org.slf4j.kotlin.getLogger


class MyClass {
    private val logger by getLogger() // or getLogger(MyClass::class)
}
```

#### Named Logger

```kotlin
val logger by getLogger("name of logger here")
```

### Logging

SLF4K uses lazy log message evaluation to avoid expensive logging messages when they're not needed.

When you write the following:

```kotlin
logger.info { "expensive message: $someVariable" }
```

this code gets transformed at compiled time and inlined into:

```kotlin
if (logger.info)
    logger.info("expensive message: $someVariable")
```

This way, the expensive message is only evaluated if the info log level is enabled.

Extensions have also been provided for every combination of level and argument. So, you can log errors as needed:

```kotlin
logger.warn(exception) { "message" }
```

### Using markers

Markers can also be really easily instantiated, as follows:

```kotlin
val myMarker = getMarker("MY_MARKER")
```

after which, they can be used normally.

### Fluent Logging

The [fluent logging api](https://www.slf4j.org/manual.html#fluent) introduced in version 2.0.0 is also supported by
SLF4K,
here are some examples of how it can be used:

```kotlin
logger.atInfo {
    message = "my message that uses string interpolation. Here is someArgument: $someArgument"
}
// equivalent to
logger.info { "my message that uses string interpolation. Here is someArgument: $someArgument" }
```

Arguments:

```kotlin
logger.atInfo {
    message = "my message here. someArgument: {} someOtherArgument: {}"
    arguments = listOf(someArgument, someOtherArgument)
}
// equivalent to
logger.info { "here is someArgument: $someArgument and here is someOtherArgument: $someOtherArgument" }
```

Key-values:

```kotlin
logger.atInfo {
    message = "my message here."
    keyValues = listOf("someArgument" to someArgument, "someOtherArgument" to someOtherArgument)
}
// equivalent to
logger.info { "someArgument=$someArgument someOtherArgument=$someOtherArgument my message here." }
```

Markers:

```kotlin
logger.atInfo {
    message = "my message here."
    // You can also provide several markers in the list
    markers = listOf(myMarker)
}
// equivalent to
logger.info(myMarker) { "my message here." }
```

Errors:

```kotlin
try {
    // do something that might thrown an exception
} catch (e: RuntimeException) {
    logger.atError {
        cause = e
        message = "my error message here"
    }
    // equivalent to
    logger.error(e) { "my error message here" }
}
```

## MDC with Coroutines

To pass MDC context between [kotlinx coroutines](https://github.com/Kotlin/kotlinx.coroutines),
use `kotlinx-coroutines-slf4j`:

### Including

#### Maven

```xml
<dependency>
  <groupId>org.jetbrains.kotlinx</groupId>
  <artifactId>kotlinx-coroutines-slf4j</artifactId>
  <version>$kotlinxCoroutinesVersion</version>
</dependency>
```

#### Gradle Groovy DSL

```groovy
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$kotlinxCoroutinesVersion'
```

#### Gradle Kotlin DSL

```kotlin
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$kotlinxCoroutinesVersion")
```

### Usage

You can propagate the MDC context through coroutines as follows:

```kt
MDC.put("kotlin", "rocks") // put a value into the MDC context

launch(MDCContext()) {
   logger.info { "..." }   // the MDC context will contain the mapping here
}
```
