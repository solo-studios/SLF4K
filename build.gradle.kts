import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.5.10"
}

group = "com.solostudios"
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