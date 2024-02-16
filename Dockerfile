FROM openjdk:21-jdk
LABEL authors="renatoctorres"
WORKDIR /app
COPY build/libs/template-spring-webflux-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]