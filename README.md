# Fullstack Developer - Java & React Assessment

## Overview

This is a full-stack e-commerce application built as a technical assessment. The project demonstrates a modern, scalable architecture using Java 17 with Spring Boot for the backend and React with TypeScript for the frontend.

The application follows a modular monolith architecture with Domain-Driven Design principles on the backend, and Atomic Design methodology for the frontend components.

## Stack Used

### Backend
- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- Spring Security with JWT
- PostgreSQL 15
- Flyway for database migrations
- MapStruct for object mapping
- Lombok for boilerplate reduction
- Springdoc for API documentation

### Frontend
- React 18
- TypeScript
- Tailwind CSS
- React Router
- Axios
- Vite

## Features

- **Authentication**: JWT-based authentication with login and registration
- **Product Management**: View, create, edit, and delete products

```

## Setup and Usage

### Prerequisites
- Java 17
- Node.js 16+
- Docker and Docker Compose
- PostgreSQL 15 (or use Docker)

### Backend Setup
1. Navigate to the backend directory:
   ```bash
   cd backend/ecommerce
   ```

2. Start the database using Docker:
   ```bash
   docker-compose -f docker/compose/dev/docker-compose.yml up -d
   ```

3. Build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run -Dspring.profiles.active=dev
   ```

4. The backend API will be available at http://localhost:8080/api
   API documentation is available at http://localhost:8080/swagger-ui.html

### Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   # or
   yarn install
   ```

3. Start the development server:
   ```bash
   npm run dev
   # or
   yarn dev
   ```

4. The frontend application will be available at http://localhost:3000

### Running with Docker Compose
You can also run the entire stack using Docker Compose:

```bash
docker-compose up -d
```

This will start the backend, frontend, and database services.

## Testing

### Backend Tests
```bash
cd backend/ecommerce
mvn test
```

### Frontend Tests
```bash
cd frontend
npm run test
# or
yarn test
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.
