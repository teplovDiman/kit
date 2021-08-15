plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("kapt")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":user"))

    compileOnly("org.mapstruct:mapstruct:" + findProperty("mapstruct_version"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    annotationProcessor("org.mapstruct:mapstruct-processor:" + findProperty("mapstruct_version"))

    testAnnotationProcessor("org.mapstruct:mapstruct-processor:" + findProperty("mapstruct_version"))

    kapt("org.mapstruct:mapstruct-jdk8:" + findProperty("mapstruct_version"))
}

tasks.getByName<Jar>("jar") { enabled = true }
tasks.getByName<Jar>("bootJar") { enabled = false }
