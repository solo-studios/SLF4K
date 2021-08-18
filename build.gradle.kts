import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    signing
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.5.10"
    id("org.jetbrains.dokka") version "1.5.0"
}

group = "ca.solo-studios"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    api("org.slf4j:slf4j-api:1.7.32")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
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

// artifacts {
//     archives(sourcesJar)
//     archives(javadocJar)
// }

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
                url.set("https://github.com/solonovamax/SLF4K")
                
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
                    url.set("https://github.com/solonovamax/SLF4K/issues")
                }
                scm {
                    connection.set("scm:git:https://github.com/solonovamax/SLF4K.git")
                    developerConnection.set("scm:git:ssh://github.com/solonovamax/SLF4K.git")
                    url.set("https://github.com/solonovamax/SLF4K/")
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
