# Spring Boot - WebFlux Template - Human Resources Process Application

![Java](http://ForTheBadge.com/images/badges/made-with-java.svg)
![Docker](https://forthebadge.com/images/badges/docker-container.svg)
![Docker](https://forthebadge.com/images/badges/docker-container.svg)
![forthebadge](https://forthebadge.com/images/badges/built-with-love.svg)
![MIT](https://forthebadge.com/images/badges/license-mit.svg)

<p style= "text-align: left;">
    <a href="https://github.com/renatoctorres/github-readme-stats/actions">
        <img alt="Tests Passing" src="https://github.com/renatoctorres/template-spring-webflux/workflows/Test/badge.svg" />
    </a>
</p>

This is an example of a process application containing a hiring process designed in a reactive model, 
exemplified with the use of APIs or Data Stream (Event-Driven)
Clean Code and Clean Architecture concepts, a test suite using JUnit, Mockito and Jacoco.
APIs documented in OpenAPI3, build and libraries managed by Maven and code quality implemented by SonarLint plugin.

## Features

- CRUD APIs for Department and Employee;
- Unit Tests and Integration Tests integrated;
- Documentation API in Swagger with Open API 3;
- Clean Code and Clean Architecture;
- Build management in Gradle;

## Build the image

```bash
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

```bash
  ./docker-compose up -d
```
2. After the MongoDB is readiness, you need now start the application

```bash
  ./gradlew bootRun
```

- The application exposes an APIs on port 8080 with these URLs:
    http://localhost:8080

- The Mongo Express is available on this endpoint:
    http://localhost:8081/

- Swagger documentation is available on this endpoint:
    http://localhost:8080/swagger-ui.html

- Open API Specification is available on this endpoint:
    http://localhost:8080/v3/api-docs


## Stacks
<p style= "text-align: left;">
     <img src="https://skillicons.dev/icons?i=java" width="48" height="48" alt="Java" /> 
     <img src="https://skillicons.dev/icons?i=spring" width="48" height="48" alt="Spring" /> 
     <img src="https://skillicons.dev/icons?i=hibernate" width="48" height="48" alt="Hibernate" />
     <img src="https://icon.icepanel.io/Technology/svg/OpenAPI.svg" width="48" height="48" alt="OpenAPI" />
     <img src="https://icon.icepanel.io/Technology/svg/Swagger.svg" width="48" height="48" alt="Swagger" />
     <img src="https://icon.icepanel.io/Technology/svg/YAML.svg" width="48" height="48" alt="YAML" />
     <img src="https://skillicons.dev/icons?i=gradle" width="48" height="48" alt="Gradle" />
     <img src="https://skillicons.dev/icons?i=github" alt="Github" />   
     <img src="https://skillicons.dev/icons?i=githubactions" alt="Github Actions" />
     <img src="https://skillicons.dev/icons?i=docker" width="48" height="48" alt="Docker" /> 
     <img src="https://skillicons.dev/icons?i=mongo" width="48" height="48" alt="MongoDB" /> 
</p>
  
