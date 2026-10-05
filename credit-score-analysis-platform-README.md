# Credit Score Analysis Platform

A microservices-based credit scoring platform designed to aggregate
customer/account information from multiple financial sources and
integrate with a third-party scoring service to generate credit scores.

## Overview

The platform demonstrates a backend workflow where a customer's
financial information is collected from multiple sources, processed
through a credit-scoring service, and used to generate a credit score.

The system is organized into three main services:

- **Credit Scoring Service** -- Handles credit-score related
  processing and integrates with the scoring service.
- **User Management Service** -- Manages user information and
  authentication.
- **Spring Cloud Gateway** -- Acts as the entry point and routes
  requests to backend services.

## Key Features

- Customer/account information aggregation from multiple financial
  sources
- Third-party credit-score calculation integration
- Microservices-based architecture
- REST-based communication between services
- Kafka-based event processing
- Redis caching for frequently accessed credit-score data
- OAuth2/JWT-based authentication
- Asynchronous email notifications for credit-score updates
- Centralized logging and monitoring using Log4j2 and Splunk

## Architecture

```text
                         Client
                           |
                           v
                +----------------------+
                | Spring Cloud Gateway |
                +----------+-----------+
                           |
             +-------------+-------------+
             |                           |
             v                           v
    +------------------+       +-------------------+
    | User Management  |       | Credit Scoring    |
    |     Service      |       |     Service       |
    +------------------+       +---------+---------+
                                          |
                              +-----------+-----------+
                              |                       |
                              v                       v
                         Third-Party               Redis
                       Scoring Service             Cache
                                                      |
                                                      v
                                                   Kafka
                                                      |
                                                      v
                                            Email Notification
```

## Credit Score Flow

1.  A customer requests or initiates a credit-score evaluation.
2.  Customer/account information is collected from the required
    financial sources.
3.  The Credit Scoring Service sends the required information to the
    third-party scoring service.
4.  The calculated credit score is stored for later retrieval.
5.  Redis is used to cache frequently requested credit-score
    information.
6.  A credit-score update is published through Kafka.
7.  The corresponding event is consumed to trigger an email
    notification.

## Microservices

### Credit Scoring Service

Responsible for the credit evaluation workflow, credit-score calculation
integration, score storage/retrieval, and credit-score update events.

### User Management Service

Responsible for user-related operations and authentication using
OAuth2/JWT.

### Spring Cloud Gateway

Provides a single entry point for clients and routes requests to the
appropriate backend microservice.

## Technology Stack

Category Technology

---

Language Java
Framework Spring Boot
Architecture Microservices
API REST
Gateway Spring Cloud Gateway
Messaging Apache Kafka
Cache Redis
Authentication OAuth2 / JWT
Logging Log4j2
Monitoring Splunk
External Integration Third-party credit scoring service

## Kafka Event Flow

Credit-score updates are published as events through Kafka. Consumers
process these events asynchronously and use the information to trigger
customer email notifications.

```text
Credit Scoring Service
        |
        v
   Kafka Topic
        |
        v
   Event Consumer
        |
        v
Email Notification
```

## Redis Caching

Redis is used to cache credit-score information that is frequently
requested.

```text
Request
   |
   v
Check Redis
   |
   +---- Cache Hit ----> Return Score
   |
   +---- Cache Miss ---> Fetch/Calculate
                              |
                              v
                         Store in Redis
                              |
                              v
                         Return Score
```

## Authentication

OAuth2/JWT authentication is used to secure access to the platform.
Authentication and user-related operations are handled through the User
Management service, while the Spring Cloud Gateway provides the entry
point for incoming requests.

## Observability

The platform uses:

- **Log4j2** for application logging
- **Splunk** for centralized log monitoring and analysis

## Project Purpose

The project demonstrates how commonly used backend technologies can work
together to build a realistic credit-scoring workflow, including
microservices communication, asynchronous event processing, caching,
authentication, external service integration, and observability.

## Future Enhancements

Potential improvements include:

- Adding stronger retry and failure-handling mechanisms for external
  scoring-service calls
- Adding automated tests for individual microservices
- Introducing containerized deployment
- Adding more detailed service-level monitoring and metrics
- Implementing stronger event-processing guarantees where required

## Author

**Vivek S**

Java Backend Developer
