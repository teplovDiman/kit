import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.apache.tools.ant.taskdefs.condition.Os
import java.io.ByteArrayOutputStream

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.avast.gradle.docker-compose")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
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

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.hibernate.validator:hibernate-validator")
    implementation("io.github.microutils:kotlin-logging-jvm:" + findProperty("kotlin_logging_version"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("com.cosium.spring.data:spring-data-jpa-entity-graph:" + findProperty("entity_graph_version"))

    runtimeOnly("org.postgresql:postgresql")

    annotationProcessor("org.mapstruct:mapstruct-processor:" + findProperty("mapstruct_version"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("com.cosium.spring.data:spring-data-jpa-entity-graph:" + findProperty("entity_graph_version"))
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:" + findProperty("mapstruct_version"))

    kapt("org.mapstruct:mapstruct-jdk8:" + findProperty("mapstruct_version"))
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

var shellType = "sh"
when {
    Os.isFamily(Os.FAMILY_WINDOWS) -> shellType = "cmd"
    Os.isFamily(Os.FAMILY_UNIX) -> shellType = "sh"
}

tasks.withType<ProcessResources> {
    // set the value in all "application**.yml" files to the placeholders with name "@gitHash@"
    filesMatching("application**.yml") {
        // todo: ugly way to get output of the commandLine
        val gitHash: String = ByteArrayOutputStream().use { outputStream ->
            project.exec {
                commandLine(shellType, "-c", "git rev-parse HEAD")
                standardOutput = outputStream
            }
            outputStream.toString()
        }
        // todo: ugly way to get output of the commandLine
        val gitTagVersion: String = ByteArrayOutputStream().use { outputStream ->
            project.exec {
                commandLine(shellType, "-c", "git tag  | grep -E '^v[0-9]' | sort -V | tail -1")
                standardOutput = outputStream
            }
            outputStream.toString()
        }

        val propertiesMap = mapOf("gitHash" to gitHash, "gitTagVersion" to gitTagVersion.trim())
        filter<org.apache.tools.ant.filters.ReplaceTokens>(mapOf("tokens" to propertiesMap))
    }
}

task<Exec>("clearUp") {
    // 1 - docker containers: down
    exec { commandLine(shellType, "-c", "yes \"y\" | docker container prune")}

    // 2 - docker image: remove application
    // todo: ugly way to get output of the commandLine
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
    composeLogToFile = File("backend/app/build/logs/composeUp.log")
    captureContainersOutputToFiles = File("backend/app/build/logs")
}

dockerCompose.isRequiredBy(project.tasks.named("bootRun").get())
dockerCompose.isRequiredBy(project.tasks.named("test").get())

project.tasks.named("clean").get().dependsOn("clearUp")
project.tasks.named("composeUp").get().dependsOn("compileTestKotlin")

project.tasks.named("assemble").get().finalizedBy("copyDockerfile")
