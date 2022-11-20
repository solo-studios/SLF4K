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
val myMarker = getMarker("MY_MARKER")
```

after which, they can be used normally.

### MDC with Coroutines

To pass MDC context between [kotlinx coroutines](https://github.com/Kotlin/kotlinx.coroutines),
use `kotlinx-coroutines-slf4j`:

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

### Gradle Kotlin DSL

```kotlin
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$kotlinxCoroutinesVersion")
```
