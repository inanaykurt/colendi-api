FROM openjdk:8-jdk-alpine
COPY target/colendi-api-0.0.1-SNAPSHOT.jar colendiApp.jar
ENTRYPOINT ["java","-jar","/colendiApp.jar"]