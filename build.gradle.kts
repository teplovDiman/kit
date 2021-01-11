buildDir = File(".build")

//group = "com.life"

//todo: edit README

plugins {
    id("org.springframework.boot") version "2.4.1" apply false
    id("io.spring.dependency-management") version "1.0.10.RELEASE" apply false
    id("com.avast.gradle.docker-compose") version "0.13.4" apply false
    id("org.flywaydb.flyway") version "7.3.2" apply false
    kotlin("jvm") version "1.4.21" apply false
    kotlin("plugin.spring") version "1.4.21" apply false
    kotlin("plugin.jpa") version "1.3.72" apply false
    kotlin("kapt") version "1.4.21" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}
