# Hotel Rating System - Microservices Architecture

A distributed microservices-based application built using Spring Boot and Spring Cloud that allows users to rate hotels and fetch complete rating details using service-to-service communication.

---

## Tech Stack

- Java

- Spring Boot

- Spring Cloud

- Eureka Service Registry

- Spring Cloud Gateway (API Gateway)

- Spring Cloud Config Server

- OpenFeign

- Resilience4j (Circuit Breaker, Retry, Fallback)

- REST APIs

- Maven

- Git & GitHub

---

## Microservices Architecture

This system is built using 6 independent microservices:

1. **API Gateway**

2. **Service Registry (Eureka Server)**

3. **Config Server**

4. **User Service**

5. **Hotel Service**

6. **Rating Service**

---

## Application Flow

### User Creates Rating

- A user can create, update, delete, and fetch ratings.

- Ratings are stored in the **Rating Service**.

- Each rating is mapped with:

  - `userId`

  - `hotelId`

---

### Fetching User with Complete Details

When a client fetches user details:

1. Request goes to **API Gateway**

2. Gateway routes request to **User Service**

3. User Service:

   - Fetches user basic details

   - Calls **Rating Service** using OpenFeign

4. Rating Service:

   - Returns ratings given by that user

   - For each rating, calls **Hotel Service**

5. Hotel Service:

   - Returns hotel details

### Final Response Contains:

- User Details

- All Ratings Given by User

- Complete Hotel Details for each Rating

This demonstrates inter-service communication in microservices architecture.

---

## Fault Tolerance (Resilience4j)

To make the system fault-tolerant:

- Circuit Breaker implemented

- Retry mechanism added

- Fallback methods configured

If any microservice fails while fetching data:

- Circuit breaker triggers

- Fallback method returns dummy/default response

- System remains stable without crashing

---

## Centralized Configuration (Config Server)

- Configuration properties are stored in a separate GitHub repository.

- Config Server fetches configurations from GitHub.

- All microservices fetch their configuration from Config Server at runtime.

- Supports centralized and dynamic configuration management.

---

## Service Registry (Eureka)

- All services register themselves with Eureka Server.

- Services discover each other using service names.

- Enables dynamic service discovery and load balancing.

---

## API Gateway

- Single entry point for all client requests.

- Routes requests to respective microservices.

- Simplifies client interaction.

- Enables centralized security and routing control.

---

## Features Implemented

- CRUD operations for Users

- CRUD operations for Hotels

- CRUD operations for Ratings

- Inter-service communication using OpenFeign

- Service Discovery using Eureka

- Centralized Configuration using Config Server

- Fault Tolerance using Resilience4j

- Fallback & Retry Mechanism

- Layered architecture (Controller → Service → Repository)

---

## Architecture Overview

Client  

⬇  

API Gateway  

⬇  

User Service  

⬇  

Rating Service  

⬇  

Hotel Service  

All services are registered with Eureka and use Config Server for centralized configuration.

---

## How to Run the Project

1. Start Service Registry (Eureka Server)

2. Start Config Server

3. Start all Microservices:

   - User Service

   - Rating Service

   - Hotel Service

4. Start API Gateway

5. Access APIs via Gateway URL https://localhost:8761

---

## Key Learning Outcomes

- Hands-on experience with Microservices Architecture

- Service-to-service communication using OpenFeign

- Circuit Breaker implementation using Resilience4j

- Centralized configuration management

- API Gateway routing

- Distributed system design principles

---

## Future Enhancements

- Dockerization of services

- Authentication using Spring Security & JWT

- Distributed tracing (Zipkin)

- Kubernetes deployment

---
