# E-commerce Monolith Application

A modular monolith e-commerce application built with Java 17, Spring Boot, and Next.js, following Domain-Driven Design principles.

## Overview

This application provides a complete e-commerce solution with the following features:
- Product catalog management
- User authentication and authorization
- Shopping cart functionality
- Order processing
- Admin dashboard
- Event-driven architecture for integration

The backend is structured as a modular monolith with clear bounded contexts, while the frontend is built with Next.js for a modern user experience.

## Architecture

The application follows a modular monolith architecture with Domain-Driven Design principles:

- **Bounded Contexts**: Clear separation of domains (product, auth, cart, order, admin, event)
- **Layered Architecture**: Each bounded context has application, domain, and infrastructure layers
- **Shared Kernel**: Core components shared across contexts
- **Schema Separation**: Database tables organized by context

For more details, see [ADR-001: Modular Monolith Architecture](decisions/adr-001-monolith.md).

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

### Frontend
- Next.js 14
- TypeScript
- Tailwind CSS
- Cypress for E2E testing

### Infrastructure
- Docker and Docker Compose
- GitHub Actions for CI/CD

## Getting Started

### Prerequisites
- Java 17
- Maven 3.8+
- Docker and Docker Compose
- Node.js 18+
- PostgreSQL 15

### Setup Database
```bash
# Start PostgreSQL using Docker
docker-compose -f docker/compose/dev/docker-compose.yml up -d
```

### Run Backend
```bash
# Navigate to backend directory
cd backend

# Build the application
mvn clean install

# Run with dev profile
mvn spring-boot:run -Dspring.profiles.active=dev
```

### Run Frontend
```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Run development server
npm run dev
```

## API Documentation

The API documentation is available at http://localhost:8080/swagger-ui.html when the application is running.

For a static OpenAPI specification, see [openapi.yaml](api/openapi.yaml).

## Testing

### Backend Tests
```bash
# Run unit tests
mvn test

# Run integration tests
mvn verify -P integration-test
```

### Frontend Tests
```bash
# Run unit tests
cd frontend
npm test

# Run E2E tests
npm run cypress:open
```

## Development Guidelines

### Backend
- Follow DDD principles with clear separation of bounded contexts
- Use records for DTOs
- Implement MapStruct for object mapping
- Apply SOLID principles
- Write comprehensive tests (unit, integration, and BDD)
- Use meaningful exception handling

### Frontend
- Follow component-based architecture
- Implement proper state management
- Use TypeScript for type safety
- Write unit tests for components and hooks
- Follow Atomic Design principles for UI components

## Deployment

### Docker Deployment
```bash
# Build and run all services
docker-compose -f docker/compose/prod/docker-compose.yml up -d
```

### CI/CD Pipeline
The project uses GitHub Actions for continuous integration and deployment:
- Build and test on every push
- Deploy to staging on merge to develop branch
- Deploy to production on merge to main branch

## Troubleshooting

### Common Issues
1. **Application exits with code 0**: Check database connectivity and credentials
2. **Database migration fails**: Verify PostgreSQL is running and accessible
3. **Frontend API calls fail**: Ensure CORS is properly configured in the backend

### Logs
- Backend logs are available in `logs/application.log`
- Structured logs can be viewed in development console or collected by ELK stack in production

## License
This project is licensed under the MIT License - see the LICENSE file for details.