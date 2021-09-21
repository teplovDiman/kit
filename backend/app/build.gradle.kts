import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.apache.tools.ant.taskdefs.condition.Os
import java.io.ByteArrayOutputStream

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.avast.gradle.docker-compose")
    id("io.gitlab.arturbosch.detekt")
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
    implementation(project(":note"))

    compileOnly("org.mapstruct:mapstruct:" + findProperty("mapstruct_version"))

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-ui:" + findProperty("openapi_version"))
    implementation("org.springdoc:springdoc-openapi-data-rest:" + findProperty("openapi_version"))
    implementation("org.springdoc:springdoc-openapi-kotlin:" + findProperty("openapi_version"))
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

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.18.1")
}

// -------------------- Configuration tasks --------------

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
    annotation("org.springframework.stereotype.Component")
    annotation("org.springframework.scheduling.annotation.Async")
    annotation("org.springframework.transaction.annotation.Transactional")
    annotation("org.springframework.cache.annotation.Cacheable")
    annotation("org.springframework.boot.test.context.SpringBootTest")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = findProperty("kotlin_compile_jvm_target").toString()
    }
}

tasks.bootJar {
    archiveFileName.set("${rootProject.name}.jar")
}

// -------------------- Kit project tasks ----------------

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
    exec { commandLine(shellType, "-c", "yes \"y\" | docker container prune") }

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

// -------------------------------------------------------

dockerCompose {
    projectName = rootProject.name
    environment.put("APPLICATION_NAME", rootProject.name)
    environment.put("POSTGRES_VOLUME_NAME", "${rootProject.name}_volume")
    useComposeFiles.add("docker-compose.yml")
    composeLogToFile.set(project.file("build/logs/composeUp.log"))
    captureContainersOutputToFiles.set(project.file("build/logs"))
}

detekt {
    buildUponDefaultConfig = true
    config = files("$projectDir/../tools/detekt/detekt-config.yml")
}

// -------------------- Task order -----------------------

dockerCompose.isRequiredBy(tasks.named("bootRun").get())
dockerCompose.isRequiredBy(tasks.named("test").get())
tasks.named("clean").get().dependsOn("clearUp")
tasks.named("composeUp").get().dependsOn("compileTestKotlin")
tasks.named("assemble").get().finalizedBy("copyDockerfile")
tasks.test { finalizedBy(tasks.jacocoTestReport) }
