plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
}

dependencies {
    implementation(project(":core"))

    compileOnly("org.mapstruct:mapstruct:" + findProperty("mapstruct_version"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

tasks.getByName<Jar>("jar") { enabled = true }
tasks.getByName<Jar>("bootJar") { enabled = false }
