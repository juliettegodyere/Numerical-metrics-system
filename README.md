# Numerical Metrics System

## Description
This is a Java application exposing 5 REST APIs. The purpose of this application will be to record numerical metric values. Each metric is defined by a name and records a count at a specific time. This service will be used by other applications to record and report on their metrics.

## Terminologies used
- Spring Boot - 3.1.1 RELEASE
- JDK - 1.8 or later
- Spring Framework - 6.0 RELEASE
- Hibernate - 6
- Spring Data JPA - 2+

## Dependencies
- Java 17
- MySQL
- Maven 3.2+

## Optional tools to aid testing
- Postman
- Spring Tool Suite (STS)
- MySQL Workbench
- CMD

## Features Implementation and endpoints
- Retrieves a list of metrics. The name of the system is required. The name of the metric, and the from and to params are optional - `GET /metrics?system_name = "Sales performance metrics"`. An example of the complete params `http://localhost:8080/metrics?system_name=Sales performance metrics&metric_name=Email reply rate&from=1688329904112&to=1688329922632`. **Note:** **Change the details to reflect the information that you have on the database.*
- Retrieve a single metric by id - `GET /metrics/{id}`
- Create a new metric, with a default value of 1, and a default date of the current date/time - `POST /metrics`
- Updates the specified metric with the supplied metric. If the value is not supplied, the existing value is incremented by 1. - `PUT /metrics/{id}`
- Retrieves a metric summary. The name of the system is required. The name of the metric, and the from and to params are optional- `GET /metricsummary?system_name=Sales performance metrics&metric_name=Email reply rate&from=1688329904112&to=1688329922632`. **Note:** **Change the details to reflect the information that you have on the database.*

## How to run the application from the Commandline

**Before running the application, change the database username and password in the `application.properties` file to use your own details**
```spring.datasource.username={YOUR_USERNAME}```
```spring.datasource.password={YOUR_PASSWORD}```

### Option 1
#### User Spring Maven plugin ```mvn spring-boot:run```
- Clone the application `https://github.com/juliettegodyere/Numerical-metrics-system.git`
- `cd Numerical-metrics-system`
- Run ```mvn spring-boot:run ``` **Note:** Use this command if you do not have Maven installed on your system.
- Run ``` mvnw spring-boot:run ``` **Note:** Use this command if you do not have Maven installed on your system. Springboot will automatically install the Apache Maven
- Run ``` mvn - Dmaven.test.skip=true spring-boot:run ``` **Note:** Use this command if you have Maven installed and you want to skip Jnit test cases
- The application will run on port 8080

### Option 1
#### ``` Import the Code into Elipse```
- Clone the application `https://github.com/juliettegodyere/Numerical-metrics-system.git`
- `cd numeric-metrics-application/target`
- run the application using on elipse. 
- The application will run on port 8080

## Completed Features
- Exception handling
- Unit tests
- Integration tests

## Ongoing Features
- API-key authentication
- Consuming relevant events on a message channel
- Containerisation
  > The Metrics application will communicate with a token-based authentication microservice via HTTP or an event-driven service like Kafka. The application utilizes Spring security and JWT. These microservices will be run on docker containers.
