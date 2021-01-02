import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.apache.tools.ant.taskdefs.condition.Os
import java.io.ByteArrayOutputStream

plugins {
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("com.avast.gradle.docker-compose") version "0.13.4"
    id("org.flywaydb.flyway") version "7.3.2"
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
    kotlin("plugin.jpa") version "1.3.72"
    kotlin("kapt") version "1.4.21"
}

val mapstructVersion by extra("1.4.1.Final")
val entityGraphVersion by extra("2.4.1")

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.mapstruct:mapstruct:$mapstructVersion")

    implementation("com.cosium.spring.data:spring-data-jpa-entity-graph:$entityGraphVersion")
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

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("com.cosium.spring.data:spring-data-jpa-entity-graph:$entityGraphVersion")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    kapt("org.mapstruct:mapstruct-jdk8:$mapstructVersion")
}

//-------------------- Configuration tasks --------------

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JavaVersion.VERSION_13.toString()
    }
}

tasks.bootJar {
    archiveFileName.set("${rootProject.name}.jar")
}

//-------------------- Kit project tasks ----------------

tasks.register<Copy>("copyDockerfile") {
    from("./docker")
    into("./build/libs")
}

tasks.register<Copy>("copyDBScripts") {
    from("./../data/migration")
    into("./build/resources/main/data/migration")
}

task<Exec>("clearUp") {
    var shellType = "sh"
    when {
        Os.isFamily(Os.FAMILY_WINDOWS) -> shellType = "cmd"
        Os.isFamily(Os.FAMILY_UNIX) -> shellType = "sh"
    }

    // 1 - docker containers: down
    exec { commandLine(shellType, "-c", "yes \"y\" | docker container prune")}

    // 2 - docker image: remove application
    val dockerImageId: String = ByteArrayOutputStream().use { outputStream ->
        project.exec {
            commandLine(shellType, "-c", "docker images -q ${rootProject.name} 2> /dev/null")
            standardOutput = outputStream
        }
        outputStream.toString()
    }
    if (dockerImageId != "") {
        exec { commandLine(shellType, "-c", "docker image rm \$(docker image ls ${rootProject.name} -q)") }
    }

    // 3 - docker volume: remove postgres
    exec { commandLine(shellType, "-c", "docker volume rm --force kit_postgres_volume || true") }

    commandLine(shellType, "-c", "echo Docker clear up finisher")
}

//-------------------------------------------------------

dockerCompose {
    projectName = rootProject.name
    environment["APPLICATION_NAME"] = rootProject.name
    environment["POSTGRES_VOLUME_NAME"] = "${rootProject.name}_volume"
    useComposeFiles.add("docker-compose.yml")
    composeLogToFile = File("backend/build/logs/composeUp.log")
    captureContainersOutputToFiles = File("backend/build/logs")
}

dockerCompose.isRequiredBy(project.tasks.named("bootRun").get())
dockerCompose.isRequiredBy(project.tasks.named("test").get())

project.tasks.named("clean").get().dependsOn("clearUp")
project.tasks.named("composeUp").get().dependsOn("compileTestKotlin")

project.tasks.named("assemble").get().finalizedBy("copyDockerfile")
project.tasks.named("processResources").get().finalizedBy("copyDBScripts")
