Kit is a project for describe, combine and use back-end technologies.
The idea is related to present a service for create, save and analyse notes.

- [Quick start](#quick-start)
- [Documentation](#documentation)
  - [Stack technology](#stack-technology)
  - [Directory Structure](#directory-structure)
  - [What quick start do](#what-quick-start-do)
  - [Connections and Debugging](#connections-and-debugging)
    - [To debug using IntelliJ](#to-debug-using-intellij)
    - [Database connection](#database-connection)
    - [Migrations](#migrations)

# Quick start

Make sure you have installed `gradle`, `docker` and `docker compose` tools and `java 16` on your machine.
The following steps expects to use them.
It was written for shell (_bash_, _cmd_, etc.) use, but you can use any alternative way.

For start the project:  
1. Go to the `kit` directory of project in your preferred command-line shell;
2. Run the `gradle build`;
3. Go to the `kit/backend/app` directory of project in your preferred command-line shell;
4. Run the `docker-compose up`;
5. For check application has been started, open browser by the following address `http://localhost:8081/api/info`.
The same result appears if send `GET` request to this URL from a REST client;
6. To stop the started containers from shell:
   - Press `CTRL + C` which will stop running docker containers;
   - Go to the `kit` directory and run `gradle clean`.
   It will down dangling docker container, clear up images, volumes and gradle build directory.
   It will __not__ remove openjdk and postgres images.

# Documentation

## Stack technology

Kit uses the following stack technology.
<pre><code>
┌── Language
|   ├── Kotlin 1.5.21
|   └── SQL (PostgreSQL Dialect)
├── Database
|   ├── Postgres 13
|   ├── Redis (not implemented yet)
|   ├── Cosium Spring Data JPA EntityGraph 2.4.1
|   └── Flyway 7.3.2
├── Test
|   ├── Cucumber (not implemented yet)
|   ├── JaCoCo (not implemented yet)
|   └── JUnit 5
└── Additional
    ├── SonarQube (not implemented yet)
    ├── Checkstyle (not implemented yet)
    ├── Kafka (not implemented yet)
    ├── Hibernate
    ├── Mapstruct
    ├── Spring Security
    ├── Swagger
    ├── Gradle 7.1.1
    ├── Kubernetes (not implemented yet)
    ├── Docker
    └── Docker compose
</code></pre>

## Directory Structure

Kit uses the following directory structure.
<pre><code>.
├── backend                              # Directory contains all backend modules and Spring Boot Starter
|   ├── app                              # The main module that depends from other and contain Spring Boot Starter
|   |  ├── docker                        # Contain Dockerfile of application
|   |  ├── src                           # Source files
|   |  ├── build.gradle                  # Gradle build file
|   |  └── docker-compose.yml            # Docker compose file with description of docker images, network and volumes
|   ├── core                             # The base module that providing some base tools for other modules
|   |  ├── src                           # Source files
|   |  └── build.gradle                  # Gradle build file
|   └── user                             # The module contain all user related functional
|      ├── src                           # Source files
|      └── build.gradle                  # Gradle build file
├── gradle                               # Gradle directory
|   └── wrapper                          # Gradle wrapper directory
|        ├── gradle-wrapper.jar          # Required for download the correct Gradle version when run the build
|        └── gradle-wrapper.properties   # To configure the wrapper’s properties such as the Gradle version
├── src                                  # Source files
├── .env                                 # Environment variables file
├── .gitignore                           # Ignoring files and folders
├── build.gradle.kts                     # Gradle kotlin build file
├── docker-compose.yml                   # Docker-compose file for local deployment
├── gradlew                              # Gradle shell script for executing the build on Linux
├── gradlew.bat                          # Gradle shell script for executing the build on Windows
├── README.md                            # Kit project readme file
└── settings.gradle                      # Kit project settings file
</code></pre>

## What quick start do

First 3 steps from [Quick start](#quick-start) is up the only back-end service.
It will download 2 docker images (java and postgres) and create the docker image of the Kit application.
Then docker compose will start 2 containers (postgres and application)   

1. `gradle build` task combine several default inner gradle tasks. Among other, it will perform several custom tasks.
When you run it, the second steps will be performed:
     * `copyDockerfile` will copy DockerFile from `./container/docker` to `./build/libs` directory.
       This file contains the instructions for a docker to build the Kit application image; 
     * When `processResources` runs, additional steps will perform:
       * Find all `application**.yml` files in project;
       * Find in each of it the placeholder with `@gitHash@` or `@gitTagVersion@` names and replace with value.
         The value is result of git commands:
         * `@gitHash@` -> `git rev-parse HEAD`. Returns the last git commit hashcode.
         * `@gitTagVersion@` -> `git tag  | grep -E '^v[0-9]' | sort -V | tail -1`.
           Returns a last git tag, that starts with `v` and continue with numbers. Example: `v0.0.1`.
           __It means that versioning in project supports and handling by git tags.__
     * Especial inner and non-default task is `composeUp`, which one added to gradle by plugin 
       [com.avast.gradle.docker-compose](https://github.com/avast/gradle-docker-compose-plugin).
       This plugin is responsible for starts the application and waits till all containers become healthy
       and all exposed TCP ports are open (so till the application is ready);
       Logs will available in `kit/backend/app/build/logs` directory;
     * Tests will be performed after containers start to be ready to work;
     * Then it down and remove the containers.
2. On this step expected that docker contains 3 images: postgres, openjdk and Kit application.
~~(Actually, Kit application will create 2 additional hides intermediate images. 
That's why `docker image ls -a` will show you 5 images. But it how's docker work).~~
So, after all of this, `docker-compose up` remains only start the containers.

3. The step №4 from [Quick start](#quick-start) suggest to call `gradle clean`,
   which one actually depends on inned custom gradle task named `clearUp`.
   * `clearUp` task will:
     * Determine which type of OS used and depends on this, call _bash_ or _cmd_ shell types;
     * Remove all stopped docker containers;
     * Check if there are Kit application image among other docker images. If yes, remove it;
     * Remove Kit application volume from docker volumes;
     * __"openjdk" and "postgres" images will alive in any way till you handle remove it.__
   * `gradle clean` will remove `./build` gradle directory;
   * After that, all traces of Kit application stay on you machine will be removed. 

## Connections and Debugging

The following steps expected already started and running Kit application inside the docker containers.
To start it quickly, follow the [Quick start](#quick-start) instructions.

### To Debug using IntelliJ

The next steps describe how to debug running Kit application under docker container via IntelliJ.  

* Open "Run/Debug Configurations" In IntelliJ
  * In the left top menu click to the "+" button named "Add New Configuration" or press `ALT + INSERT`
  * Choose "Remote" type configuration
  * On the right part of window, set the next settings:
    * Name: "Debug" or as you want
    * Host: `localhost`
    * Port: `10005`
    * Make sure the "Command line arguments for remote JVM" looks like
      `-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:10005` 
  * Press "Apply" and "OK" button for save settings
* Choose a new configuration in the list of configurations
* Click "Debug" button or press `SHIFT + F9`

### Database connection

The postgres container is available from your local machine on port `54320` and can be accessed directly using the
`psql -h localhost -p 54320` command.
Or use can configure connection using you preferred IDE with next parameters:
* Host: `localhost`
* Port: `54320`
* User: `postgres`
* Password: `123`
* Database: `kit`
* Make sure the URL looks like `jdbc:postgresql://localhost:54320/kit`

### Migrations

[Flyway](https://flywaydb.org/) are handle database migrations by execute them from the
`src/main/resources/db/migration/<module_name>` directory per each module.
Migrations should follow the flyway naming convention which is like `V1__Versioned_migration.sql`.
More details described in Flyway documentations.
Versioned migrations will be applied out-of-order by default (although this is configurable).

Also, Kit application realised mechanism for separate migrations by the modules.
It was done for the following reasons: each module must be isolated from other and be knowledgeable only about common
modules, that described explicitly in gradle file of module.
For these reason, each module contains only those migrations, that relate to this very module.

This mechanism interacts directly with the Spring context by asking the implementations of the `FlywayConfig` interface.
In turn, each module must implement the `FlywayConfig` interface: specify a schema name and `classpath`
that contains its migrations.
To start this mechanism, the Spring run the `KitFlywayMigrationStrategy` class.
This class first launches Flyway for its own module and then launches any `FlywayConfig` implementations it finds.
