/*
 * SLF4K - A set of SLF4J extensions for Kotlin to make logging more idiomatic.
 * Copyright (c) 2021-2023 solonovamax <solonovamax@12oclockpoint.com>
 *
 * The file build.gradle.kts is part of SLF4K
 * Last modified on 19-01-2023 02:11 p.m.
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
import pl.allegro.tech.build.axion.release.domain.hooks.HookContext
import java.net.URL
import java.time.Year
import kotlin.math.max

plugins {
    java
    signing
    `java-library`
    `maven-publish`
    
    alias(libs.plugins.axion.release)
    
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.dokka)
    
    alias(libs.plugins.kotlinter)
}

scmVersion {
    hooks {
        pre(
            "fileUpdate", mapOf(
                "encoding" to "UTF-8",
                "file" to file("README.md"),
                "pattern" to KotlinClosure2({ previousVersion: String, _: HookContext ->
                                                println("previous: $previousVersion")
                                                previousVersion
                                            }),
                "replacement" to KotlinClosure2({ currentVersion: String, _: HookContext ->
                                                    println("current: $currentVersion")
                                                    currentVersion
                                                })
            )
        )
    }
}

group = "ca.solo-studios"
version = scmVersion.version

repositories {
    maven("https://maven.solo-studios.ca/releases/")
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    
    withSourcesJar()
    withJavadocJar()
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

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    
    api(libs.slf4j)
    
    compileOnlyApi(libs.kotlinx.coroutines)
    compileOnlyApi(libs.kotlinx.coroutines.slf4j)
    
    kspTest(libs.ksp.service)
    testCompileOnly(libs.ksp.service)
    
    
    testImplementation(libs.slf4j.simple)
    
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.coroutines.debug)
    
    testImplementation(libs.bundles.junit)
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
    
    val processDokkaIncludes by register("processDokkaIncludes", ProcessResources::class) {
        from(projectDir.resolve("dokka/includes")) {
            val projectInfo = ProjectInfo(project.group.toString(), project.name, version.toString())
            filesMatching("Module.md") {
                expand(
                    "project" to projectInfo,
                    "versions" to mapOf(
                        "slf4j" to libs.versions.slf4j.get(),
                        "kotlinxCoroutines" to libs.versions.kotlinx.coroutines.get(),
                    ),
                )
            }
        }
        destinationDir = buildDir.resolve("dokka-include")
        group = JavaBasePlugin.DOCUMENTATION_GROUP
    }
    
    withType<DokkaTask>().configureEach {
        dependsOn(processDokkaIncludes)
        
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            footerMessage = "© ${Year.now()} Copyright solo-studios"
            separateInheritedMembers = true
        }
        
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

val dokkaHtml by tasks.getting(DokkaTask::class)

val javadoc by tasks.getting(Javadoc::class)

val jar by tasks.getting(Jar::class)

val javadocJar by tasks.getting(Jar::class) {
    dependsOn(dokkaHtml)
    from(dokkaHtml.outputDirectory)
    archiveClassifier.set("javadoc")
    group = JavaBasePlugin.DOCUMENTATION_GROUP
}

val sourcesJar by tasks.getting(Jar::class) {
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
            from(components["java"])
            
            version = version as String
            groupId = group as String
            artifactId = "slf4k"
            
            pom {
                val projectOrg = "solo-studios"
                val projectRepo = "SLF4K"
                val githubBaseUri = "github.com/$projectOrg/$projectRepo"
                val githubUrl = "https://$githubBaseUri"
                
                name.set("SLF4K")
                description.set("A set of SLF4J extensions for Kotlin to make logging more idiomatic.")
                url.set(githubUrl)
                
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
                    url.set("$githubUrl/issues")
                }
                scm {
                    connection.set("scm:git:$githubUrl.git")
                    developerConnection.set("scm:git:ssh://$githubBaseUri.git")
                    url.set(githubUrl)
                }
            }
        }
    }
    
    repositories {
        maven {
            name = "Sonatype"
            
            val releasesUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") // releases repo
            val snapshotUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/") // snapshot repo
            url = if (isSnapshot) snapshotUrl else releasesUrl
            
            credentials(PasswordCredentials::class)
        }
        maven {
            name = "SoloStudios"
            
            val releasesUrl = uri("https://maven.solo-studios.ca/releases/")
            val snapshotUrl = uri("https://maven.solo-studios.ca/snapshots/")
            url = if (isSnapshot) snapshotUrl else releasesUrl
            
            credentials(PasswordCredentials::class)
            authentication { // publishing doesn't work without this for some reason
                create<BasicAuthentication>("basic")
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["maven"])
}

val Project.isSnapshot: Boolean
    get() = version.toString().endsWith("-SNAPSHOT")

/**
 * Project info class for [processDokkaIncludes].
 */
data class ProjectInfo(val group: String, val module: String, val version: String)
