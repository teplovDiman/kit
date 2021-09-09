plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
}

dependencies {
    compileOnly("org.mapstruct:mapstruct:" + findProperty("mapstruct_version"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-ui:" + findProperty("openapi_version"))
    implementation("org.springdoc:springdoc-openapi-data-rest:" + findProperty("openapi_version"))
    implementation("org.springdoc:springdoc-openapi-kotlin:" + findProperty("openapi_version"))
    implementation("io.github.microutils:kotlin-logging-jvm:" + findProperty("kotlin_logging_version"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
}

tasks.getByName<Jar>("jar") { enabled = true }
tasks.getByName<Jar>("bootJar") { enabled = false }
