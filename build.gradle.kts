/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2021-2022 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file build.gradle.kts is part of SLF4K
 * Last modified on 20-11-2022 11:25 a.m.
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

import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jmailen.gradle.kotlinter.tasks.InstallPreCommitHookTask

plugins {
    java
    signing
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.7.10"
    id("org.jetbrains.dokka") version "1.7.20"
    id("org.jmailen.kotlinter") version "3.11.1"
    id("com.google.devtools.ksp") version "1.7.20-1.0.7"
}

group = "ca.solo-studios"
version = "0.4.6"

repositories {
    mavenCentral()
}

kotlin {
    explicitApi()
    target {
        compilations.configureEach {
            kotlinOptions {
                allWarningsAsErrors = true
                jvmTarget = "1.8"
                apiVersion = "1.6"
                languageVersion = "1.6"
            }
        }
    }
}

kotlinter {
    ignoreFailures = false
    // indentSize = 4
    reporters = arrayOf("checkstyle", "html", "json", "plain")
    experimentalRules = true
    disabledRules = arrayOf(
        "no-multi-spaces",
        "no-consecutive-blank-lines",
        "indent",
        "no-trailing-spaces",
        "multiline-if-else",
    )
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    
    api("org.slf4j:slf4j-api:2.0.3")
    
    compileOnlyApi("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    // compileOnlyApi("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.6.4")
    
    kspTest("ca.solo-studios:ksp-service-annotation:1.0.1")
    kspTest("com.squareup:kotlinpoet:1.12.0") // idk why this isn't being included
    testCompileOnly("ca.solo-studios:ksp-service-annotation:1.0.1")
    
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    testImplementation("org.slf4j:slf4j-simple:2.0.3")
}

val installKotlinterPreCommitHook by tasks.registering(InstallPreCommitHookTask::class) {
    group = "build setup"
    description = "Installs Kotlinter Git pre-commit hook"
}

tasks.check {
    dependsOn(installKotlinterPreCommitHook)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val dokkaHtml by tasks.getting(DokkaTask::class)

val javadoc by tasks.getting(Javadoc::class)

val jar by tasks.getting(Jar::class)

val javadocJar by tasks.creating(Jar::class) {
    dependsOn(dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaHtml.outputDirectory)
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

artifacts {
    archives(sourcesJar)
    archives(javadocJar)
    archives(jar)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(sourcesJar)
            artifact(javadocJar)
            artifact(jar)
            
            version = version as String
            groupId = group as String
            artifactId = "slf4k"
            
            pom {
                name.set("SLF4K")
                description.set("A set of SLF4J extensions for Kotlin to make logging more idiomatic.")
                url.set("https://github.com/solo-studios/SLF4K")
                
                inceptionYear.set("2021")
                
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://mit-license.org/")
                    }
                }
                developers {
                    developer {
                        id.set("solonovamax")
                        name.set("solonovamax")
                        email.set("solonovamax@12oclockpoint.com")
                        url.set("https://github.com/solonovamax")
                    }
                }
                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/solo-studios/SLF4K/issues")
                }
                scm {
                    connection.set("scm:git:https://github.com/solo-studios/SLF4K.git")
                    developerConnection.set("scm:git:ssh://github.com/solo-studios/SLF4K.git")
                    url.set("https://github.com/solo-studios/SLF4K/")
                }
            }
        }
    }
    
    repositories {
        maven {
            name = "sonatypeStaging"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2")
            credentials(org.gradle.api.credentials.PasswordCredentials::class)
        }
        maven {
            name = "sonatypeSnapshot"
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            credentials(PasswordCredentials::class)
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["maven"])
}
