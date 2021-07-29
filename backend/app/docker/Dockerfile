FROM openjdk:16
COPY kit.jar /kit.jar
EXPOSE 8081
ENTRYPOINT ["java", "-Xdebug", "-Xrunjdwp:transport=dt_socket,address=*:8000,server=y,suspend=n", "-jar", "kit.jar", "-Djava.awt.headless=true"]
