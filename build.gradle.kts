import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("com.avast.gradle.docker-compose") version "0.13.0"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
    kotlin("plugin.jpa") version "1.3.72"
    kotlin("kapt") version "1.4.10"
}

group = "com.life"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_14

val mapstructVersion by extra("1.3.1.Final")
val entityGraphVersion by extra("2.3.1")

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.cosium.spring.data:spring-data-jpa-entity-graph:$entityGraphVersion")
    compileOnly("org.mapstruct:mapstruct:$mapstructVersion")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.hibernate.validator:hibernate-validator")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    runtimeOnly("org.postgresql:postgresql")

    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        // exclude migration from Junit 4 to Junit 5
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("com.cosium.spring.data:spring-data-jpa-entity-graph:$entityGraphVersion")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    kapt("org.mapstruct:mapstruct-jdk8:$mapstructVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

//tasks.register<Exec>("gitBranchName") {
//    val command = "git rev-parse --abbrev-ref HEAD"
//    commandLine = listOf("cmd", "/c", command)
//}

//tasks.register<Exec>("dbReset") {
//    val command = "docker volume rm ${project.name} || true"
//    commandLine = listOf("cmd", "/c", command)
//}

dockerCompose {
    environment["POSTGRES_VOLUME_NAME"] = "${project.name}_pgdata"
    useComposeFiles.add("docker-compose.yml")
    composeLogToFile = File("build/container-logs/composeUp.log")
    captureContainersOutputToFiles = File("build/container-logs")
}

dockerCompose.isRequiredBy(project.tasks.named("bootRun").get())
dockerCompose.isRequiredBy(project.tasks.named("test").get())
