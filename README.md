Kit is a project for describe, combine and use back-end technologies.
The idea is relate to present a service for create, save and analyse notes.

- [Quick start](#quick-start)
- [Documentation](#documentation)
  - [Stack technology](#stack-technology)
  - [Directory Structure](#directory-structure)
  - [What quick start do](#what-quick-start-do)
  - [Connections and Debugging](#connections-and-debugging)
    - [To debug using IntelliJ](#to-debug-using-intellij)
    - [Database connection](#database-connection)

# Quick start

Make sure you have installed `gradle`, `docker` and `docker compose` tools on your machine.
The following steps expects to use them.
The following steps was written for shell (_bash_, _cmd_, etc) use, but you can use any alternative way.

For start the project:  
1. Open the directory of project (kit);
2. Run the `gradle build`;
3. Run the `docker-compose up`;
4. For check application has been started, try to open browser by the following address http://localhost:8081/api/version or send `GET` request to this URL from a REST client;
5. To stop the started containers from shell:
   - Press `CTRL + C` which will stop running docker containers;
   - `gradle clean` will down dangling docker container, clear up images, volumes and then gradle build directory. It will __not__ remove openjdk and postgres images.

# Documentation

## Stack technology

Kit uses the following stack technology.
<pre><code>
┌── Language
|   ├── Kotlin 1.4.21
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
    ├── Gradle 6.4.1
    ├── Kubernetes (not implemented yet)
    ├── Docker
    └── Docker compose
</code></pre>

## Directory Structure

Kit uses the following directory structure.
<pre><code>.
├── container                            # Spring Boot Dockerfile
├── data                                 # Set of DB scripts
|   └── db                               # PosgreSQL database SQL scripts
|      └── migration                     # Kit Flyway SQL scripts
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

1. When you run `gradle build` task, the second steps will performed:
   * `gradle build` task combine several default inner gradle tasks. Among other, it will perform several custom tasks. Such as:
     * `copyDBScripts` will copy all sql-scripts from `./data` to `./build/resources/main` directory. These scripts are expected by Flyway for run it's against a database;
     * `copyDockerfile` will copy DockerFile from `./container/docker` to `./build/libs` directory. This file contains the instructions for a docker to build the Kit application image; 
     * Especial inner and non-default task is `composeUp`, which one added to gradle by plugin [com.avast.gradle.docker-compose](https://github.com/avast/gradle-docker-compose-plugin).
       This plugin is responsible for starts the application and waits till all containers become healthy and all exposed TCP ports are open (so till the application is ready);
     * Tests will performed after containers start to be ready to work;
     * Then it down and remove the containers.
2. On this step expected that docker contains 3 images: postgres, openjdk and Kit application.
~~(Actually, Kit application will create 2 additional hides intermediate images. That's why `docker image ls -a` will show you 5 images. But it how's docker work).~~
So, after all of this, `docker-compose up` remains only start the containers.

3. The step №4 from [Quick start](#quick-start) suggest to call `gradle clean`, which one actually depends on inned custom gradle task named `clearUp`.
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

* Open "Run/Debug Configurations" In IntelliJ
  * In the left top menu click to the "+" button named "Add New Configuration" or press `ALT + INSERT`
  * Choose "Remote" type configuration
  * On the right part of window, set the next settings:
    * Name: "Debug" or as you want
    * Host: `localhost`
    * Port: `10005`
    * Make sure the "Command line arguments for remote JVM" looks like `-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:10005` 
  * Press "Apply" and "OK" button for save settings
* Choose a new configuration in the list of configurations
* Click "Debug" button or press `SHIFT + F9`

### Database connection

The postgres container is available from your local machine on port `5432` and can be accessed directly using the `psql -h localhost -p 5432` command.
Or use can configure connection using you preferred IDE with next parameters:
* Host: `localhost`
* Port: `5432`
* User: `postgres`
* Password: `123`
* Database: `kit`
* Make sure the URL looks like `jdbc:postgresql://localhost:5432/kit`
