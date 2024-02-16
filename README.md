# Spring Boot - WebFlux Template - Human Resources Process Application

This is an example of a process application containing a hiring process designed in a reactive model, 
exemplified with the use of APIs or Data Stream (Event-Driven)
Clean Code and Clean Architecture concepts, a test suite using JUnit, Mockito and Jacoco.
APIs documented in OpenAPI3, build and libraries managed by Maven and code quality implemented by SonarLint plugin.

## Features

- CRUD APIs for Department and Employer;
- Unit Tests and Integration Tests integrated;
- Documentation API in Swagger with Open API 3;
- Clean Code and Clean Architecture;
- Build management in Gradle;

## Build the image

```
gradle clean spring-boot:build-image
```
Requirements: Docker daemon on the build computer
(https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/#build-image).

## Configuration

The docker-compose.yaml file contains setup to up MongoDB 
The application.yaml,  contains values for local development.

```
spring.data.mongodb.uri = mongodb://localhost:27017/spring-webflux
```


## Run Local

1. First you need up the MongoDB image in Docker

```
./docker-compose up -d
```
2. After the MongoDB is readiness, you need now start the application

```
./gradlew bootRun

```
The application exposes an APIs on port 8080 with these URLs:
http://localhost:8080

Swagger documentation is available on this endpoint:
http://localhost:8080/swagger-ui/index.html

Open API Specification is available on this endpoint:
http://localhost:8080/v3/api-docs

## Stacks
![image](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![image](https://img.shields.io/badge/Kotlin-B125EA&style=for-the-badge&logo=kotlin&logoColor=white)
![image](https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![image](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)
![image](https://img.shields.io/badge/Elastic_Search-005571?style=for-the-badge&logo=elasticsearch&logoColor=white)
![image](https://img.shields.io/badge/Kibana-005571?style=for-the-badge&logo=Kibana&logoColor=white)
![image](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white)  
