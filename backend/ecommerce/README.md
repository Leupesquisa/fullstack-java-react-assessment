# E-commerce Monolith Application

A modular monolith e-commerce application built with Java 17, Spring Boot, and following Domain-Driven Design principles.

## Overview

This application provides a complete e-commerce solution with the following features:
- Product catalog management
- User authentication and authorization
- Shopping cart functionality
- Order processing
- Admin dashboard
- Event-driven architecture for integration

## Architecture

The application follows a modular monolith architecture with Domain-Driven Design principles:

- **Bounded Contexts**: Clear separation of domains (product, auth, cart, order, admin, event)
- **Layered Architecture**: Each bounded context has application, domain, and infrastructure layers
- **Shared Kernel**: Core components shared across contexts
- **Schema Separation**: Database tables organized by context

## Tech Stack

### Backend
- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- Spring Security
- PostgreSQL 15
- Flyway for database migrations
- MapStruct for object mapping
- Lombok for boilerplate reduction
- Springdoc for API documentation

## Project Structure

```
io.github.leupesquisa.ecommerce/
├── [bounded-context]/
│   ├── application/
│   │   ├── dto/
│   │   ├── mapper/
│   │   ├── service/
│   │   └── [Controller].java
│   ├── domain/
│   │   ├── [Entity].java
│   │   ├── [ValueObject].java
│   │   └── [DomainService].java
│   └── infrastructure/
│       ├── [Repository].java
│       └── [Adapter].java
└── core/
    ├── config/
    ├── audit/
    ├── exception/
    ├── filter/
    └── logging/
```

## Key Features

### Record-based DTOs
All DTOs are implemented as Java records for immutability and automatic getter/equals/hashCode/toString generation.

```java
public record ProductResponseDto(
    UUID id,
    String sku,
    String name,
    String description,
    BigDecimal price,
    Integer stock,
    String category,
    String imageUrl,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
```

### MapStruct for Mappings
MapStruct is used for object mapping between entities and DTOs.

```java
@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDto toResponseDto(Product product);
    Product toEntity(ProductRequestDto requestDto);
}
```

### Single Responsibility Principle
Each class has a single responsibility, following the SRP principle.

```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final EventPublisher eventPublisher;
    
    // Methods for product operations
}
```

## Getting Started

### Prerequisites
- Java 17
- Maven 3.8+
- Docker and Docker Compose
- PostgreSQL 15

### Setup Database
```bash
# Start PostgreSQL using Docker
docker-compose -f docker/compose/dev/docker-compose.yml up -d
```

### Run Application
```bash
# Build the application
mvn clean install

# Run with dev profile
mvn spring-boot:run -Dspring.profiles.active=dev
```

### Run Tests
```bash
# Run unit tests
mvn test

# Run integration tests
mvn verify -P integration-test
```

## API Documentation

The API documentation is available at http://localhost:8080/swagger-ui.html when the application is running.

## Cross-cutting Concerns

### Caching
The application uses Spring's caching support to improve performance for frequently accessed data.

### Logging
A centralized logging aspect is implemented to log method entry/exit and exceptions.

### Rate Limiting
API rate limiting is implemented to protect against abuse.

### Request/Response Monitoring
Request and response details are logged for monitoring purposes.

## License

This project is licensed under the MIT License - see the LICENSE file for details.