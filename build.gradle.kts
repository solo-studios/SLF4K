/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2021-2022 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file build.gradle.kts is part of SLF4K
 * Last modified on 20-11-2022 03:26 p.m.
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

@file:Suppress("UnstableApiUsage")

import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask
import org.jmailen.gradle.kotlinter.tasks.InstallPreCommitHookTask
import java.net.URL
import java.time.Year
import kotlin.math.max

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
val versionObj = Version("0", "5", "1")
version = versionObj.toString()

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
        "filename"
    )
}

val slf4jVersion = "2.0.3"
val kotlinxCoroutinesVersion = "1.6.4"

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    
    api("org.slf4j:slf4j-api:$slf4jVersion")
    
    compileOnlyApi("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
    // compileOnlyApi("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.6.4")
    
    kspTest("ca.solo-studios:ksp-service-annotation:1.0.1")
    kspTest("com.squareup:kotlinpoet:1.12.0") // idk why this isn't being included
    testCompileOnly("ca.solo-studios:ksp-service-annotation:1.0.1")
    
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")
}

tasks {
    val installKotlinterPreCommitHook by registering(InstallPreCommitHookTask::class) {
        group = "build setup"
        description = "Installs Kotlinter Git pre-commit hook"
    }
    
    check {
        dependsOn(installKotlinterPreCommitHook)
    }
    
    withType<Test>().configureEach {
        useJUnitPlatform()
        
        failFast = false
        maxParallelForks = max(Runtime.getRuntime().availableProcessors() - 1, 1)
    }
    
    withType<Javadoc>().configureEach {
        options {
            encoding = "UTF-8"
        }
    }
    
    withType<Jar>().configureEach {
        metaInf {
            from(rootProject.file("LICENSE"))
        }
    }
    
    withType<DokkaTask>().configureEach {
        dependsOn(processDokkaIncludes)
        
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            footerMessage = "© ${Year.now()} Copyright solo-studios"
            separateInheritedMembers = true
        }
        suppressInheritedMembers
        
        dokkaSourceSets.configureEach {
            includes.from(processDokkaIncludes.destinationDir.listFiles())
            
            jdkVersion.set(8)
            reportUndocumented.set(true)
            
            // Documentation link
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URL("https://github.com/solo-studios/SLF4K/tree/master/src/main/kotlin"))
                remoteLineSuffix.set("#L")
            }
            
            externalDocumentationLink("https://www.slf4j.org/apidocs/")
        }
        
        group = JavaBasePlugin.DOCUMENTATION_GROUP
    }
}

val processDokkaIncludes by tasks.register("processDokkaIncludes", ProcessResources::class) {
    from(projectDir.resolve("dokka/includes")) {
        val projectInfo = ProjectInfo(project.group.toString(), project.name, versionObj)
        filesMatching("Module.md") {
            expand(
                "project" to projectInfo,
                "versions" to mapOf(
                    "slf4j" to slf4jVersion,
                    "kotlinxCoroutines" to kotlinxCoroutinesVersion,
                ),
            )
        }
    }
    destinationDir = buildDir.resolve("dokka-include")
    group = JavaBasePlugin.DOCUMENTATION_GROUP
}

val dokkaHtml by tasks.getting(DokkaTask::class)

val javadoc by tasks.getting(Javadoc::class)

val jar by tasks.getting(Jar::class)

val javadocJar by tasks.registering(Jar::class) {
    dependsOn(dokkaHtml)
    from(dokkaHtml.outputDirectory)
    archiveClassifier.set("javadoc")
    group = JavaBasePlugin.DOCUMENTATION_GROUP
}

val sourcesJar by tasks.registering(Jar::class) {
    from(sourceSets["main"].allSource)
    archiveClassifier.set("sources")
    group = JavaBasePlugin.DOCUMENTATION_GROUP
}

artifacts {
    archives(jar)
    archives(sourcesJar)
    archives(javadocJar)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(jar)
            artifact(sourcesJar)
            artifact(javadocJar)
    
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

/**
 * Version class, which does version stuff.
 */
data class Version(val major: String, val minor: String, val patch: String) {
    override fun toString(): String = "$major.$minor.$patch"
}

/**
 * Project info class for [processDokkaIncludes].
 */
data class ProjectInfo(val group: String, val module: String, val version: Version)
