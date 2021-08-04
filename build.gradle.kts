buildDir = File(".build")

plugins {
    id("org.springframework.boot") version "2.5.3" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    id("com.avast.gradle.docker-compose") version "0.13.4" apply false
    id("org.flywaydb.flyway") version "7.3.2" apply false
    kotlin("jvm") version "1.5.21" apply false
    kotlin("plugin.spring") version "1.5.21" apply false
    kotlin("plugin.jpa") version "1.5.21" apply false
    kotlin("kapt") version "1.5.21" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}
