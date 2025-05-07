Hi June,

I need to set up a professional GitHub repository for a technical challenge titled "Fullstack Developer - Java & React Assessment".

Please follow these instructions:

1. **Connect my GitHub account to IntelliJ** and make sure authentication is working correctly.
2. **Initialize a local Git repository** for this IntelliJ project.
3. **Create a `.gitignore`** suitable for a full stack project using Java 17 (Spring Boot) and React, excluding:
   - IDE/config files (`.idea/`, `.vscode/`)
   - build folders (`target/`, `build/`, `dist/`, `out/`)
   - `node_modules/`, `.env`, logs, and OS-specific files
4. **Include only essential, production-level assets**:
   - Clean, modular source code:
      - `backend/` (Java 17 + Spring Boot REST API)
      - `frontend/` (React SPA)
   - `README.md` with:
      - Challenge title
      - Overview of the solution
      - Stack used (Java 17, Spring Boot, React, etc.)
      - Setup and usage instructions
      - Link to the deployed API or frontend (if applicable)
   - `LICENSE` (MIT)
   - `docker-compose.yml` and `Dockerfile` (if available)
   - `pom.xml` or `build.gradle`, and `package.json` with dependencies
   - `.env.example` (no secrets)
   - Test files (unit/integration)
   - Any additional `docs/` or `scripts/` folders used
5. **Do NOT include**:
   - Real credentials (`.env`, keys, passwords)
   - IDE folders or config files
   - Temporary/test data or unnecessary binaries/assets

Then:
- Create a new **private** repository on GitHub called: `fullstack-java-react-assessment`
- Push all prepared content to the GitHub repository
- Copy the **repository link** and confirm that it's ready to be shared with the challenge reviewers

Let me know once everything is set up correctly.
I need to set up a professional GitHub repository for a technical challenge titled "Fullstack Developer - Java & React Assessment".

Please follow these instructions:

1. **Connect my GitHub account to IntelliJ** and make sure authentication is working correctly.
2. **Initialize a local Git repository** for this IntelliJ project.
3. **Create a `.gitignore`** suitable for a full stack project using Java 17 (Spring Boot) and React, excluding:
   - IDE/config files (`.idea/`, `.vscode/`)
   - build folders (`target/`, `build/`, `dist/`, `out/`)
   - `node_modules/`, `.env`, logs, and OS-specific files
4. **Include only essential, production-level assets**:
   - Clean, modular source code:
      - `backend/` (Java 17 + Spring Boot REST API)
      - `frontend/` (React SPA)
   - `README.md` with:
      - Challenge title
      - Overview of the solution
      - Stack used (Java 17, Spring Boot, React, etc.)
      - Setup and usage instructions
      - Link to the deployed API or frontend (if applicable)
   - `LICENSE` (MIT)
   - `docker-compose.yml` and `Dockerfile` (if available)
   - `pom.xml` or `build.gradle`, and `package.json` with dependencies
   - `.env.example` (no secrets)
   - Test files (unit/integration)
   - Any additional `docs/` or `scripts/` folders used
5. **Do NOT include**:
   - Real credentials (`.env`, keys, passwords)
   - IDE folders or config files
   - Temporary/test data or unnecessary binaries/assets

Then:
- Create a new **private** repository on GitHub called: `fullstack-java-react-assessment`
- Push all prepared content to the GitHub repository
- Copy the **repository link** and confirm that it's ready to be shared with the challenge reviewers

Let me know once everything is set up correctly.
I need to set up a professional GitHub repository for a technical challenge titled "Fullstack Developer - Java & React Assessment".

Please follow these instructions:

1. **Connect my GitHub account to IntelliJ** and make sure authentication is working correctly.
2. **Initialize a local Git repository** for this IntelliJ project.
3. **Create a `.gitignore`** suitable for a full stack project using Java 17 (Spring Boot) and React, excluding:
   - IDE/config files (`.idea/`, `.vscode/`)
   - build folders (`target/`, `build/`, `dist/`, `out/`)
   - `node_modules/`, `.env`, logs, and OS-specific files
4. **Include only essential, production-level assets**:
   - Clean, modular source code:
      - `backend/` (Java 17 + Spring Boot REST API)
      - `frontend/` (React SPA)
   - `README.md` with:
      - Challenge title
      - Overview of the solution
      - Stack used (Java 17, Spring Boot, React, etc.)
      - Setup and usage instructions
      - Link to the deployed API or frontend (if applicable)
   - `LICENSE` (MIT)
   - `docker-compose.yml` and `Dockerfile` (if available)
   - `pom.xml` or `build.gradle`, and `package.json` with dependencies
   - `.env.example` (no secrets)
   - Test files (unit/integration)
   - Any additional `docs/` or `scripts/` folders used
5. **Do NOT include**:
   - Real credentials (`.env`, keys, passwords)
   - IDE folders or config files
   - Temporary/test data or unnecessary binaries/assets

Then:
- Create a new **private** repository on GitHub called: `fullstack-java-react-assessment`
- Push all prepared content to the GitHub repository
- Copy the **repository link** and confirm that it's ready to be shared with the challenge reviewers

Let me know once everything is set up correctly.
a# E-commerce Full Stack Application

## Project Overview
This project is a full-stack e-commerce application built to demonstrate proficiency in both Java (backend) and React (frontend) development. It implements a product catalog system with user authentication, following best practices for full-stack development.

## Tech Stack

### Backend
- **Java 17**: Core programming language
- **Spring Boot 3.4.5**: Application framework
- **Spring Data JPA**: Data access and ORM
- **Spring Security**: Authentication and authorization
- **PostgreSQL**: Relational database
- **Flyway**: Database migration
- **JWT**: Token-based authentication
- **MapStruct**: Object mapping between entities and DTOs
- **Lombok**: Reduces boilerplate code
- **Validation API**: Input validation

### Frontend
- **React**: UI library (via Next.js 14)
- **TypeScript**: Type-safe JavaScript
- **Tailwind CSS**: Utility-first CSS framework
- **Axios**: HTTP client for API requests
- **React Query**: Data fetching and state management
- **React Hook Form**: Form handling and validation

## Project Structure

### Backend Structure
```
src/main/java/io/github/leupesquisa/ecommerce/
├── product/                  # Product bounded context
│   ├── application/          # Application layer
│   │   ├── controller/       # REST controllers
│   │   ├── dto/              # Data Transfer Objects
│   │   ├── mapper/           # Object mappers
│   │   └── service/          # Business logic
│   ├── domain/               # Domain layer
│   │   ├── model/            # Domain entities
│   │   └── exception/        # Domain exceptions
│   └── infrastructure/       # Infrastructure layer
│       └── repository/       # Data access
├── auth/                     # Authentication bounded context
├── cart/                     # Shopping cart bounded context
├── order/                    # Order processing bounded context
└── core/                     # Shared kernel
    ├── config/               # Configuration classes
    ├── exception/            # Global exception handling
    └── audit/                # Auditing functionality
```

### Frontend Structure
```
src/
├── app/                      # Next.js app router
│   ├── api/                  # API routes
│   ├── auth/                 # Authentication pages
│   ├── products/             # Product pages
│   ├── cart/                 # Cart pages
│   └── orders/               # Order pages
├── components/               # React components
│   ├── ui/                   # UI components
│   ├── forms/                # Form components
│   └── layout/               # Layout components
├── lib/                      # Utility functions
├── hooks/                    # Custom React hooks
└── types/                    # TypeScript type definitions
```

## Key Features

### Backend Features
1. **RESTful API**: Complete CRUD operations for products
   - GET /api/products - List all products with pagination
   - GET /api/products/{id} - Get product details
   - POST /api/products - Create new product
   - PUT /api/products/{id} - Update product
   - DELETE /api/products/{id} - Delete product

2. **Data Validation**:
   - Input validation using Jakarta Validation API
   - Custom validation error responses
   - DTO pattern for request/response separation

3. **Error Handling**:
   - Global exception handler
   - Consistent error response format
   - Domain-specific exceptions

4. **Authentication**:
   - JWT-based authentication
   - User registration and login
   - Role-based authorization

### Frontend Features
1. **User Authentication**:
   - Login/Registration forms
   - Protected routes
   - JWT token management

2. **Data Fetching**:
   - API integration with Axios
   - Loading states and error handling
   - Data caching with React Query

3. **Dynamic Rendering**:
   - Responsive product listings
   - Product details view
   - Shopping cart management
   - Order processing

## Running the Application

### Prerequisites
- Java 17
- Maven 3.8+
- Node.js 18+
- PostgreSQL 15
- Docker (optional)

### Backend Setup
```bash
# Start PostgreSQL
docker-compose -f docker/compose/dev/docker-compose.yml up -d

# Build and run the application
mvn clean install
mvn spring-boot:run -Dspring.profiles.active=dev
```

### Frontend Setup
```bash
cd frontend
npm install
npm run dev
```

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
cd frontend
npm test
```

## Best Practices

### Backend Best Practices
- **DDD Principles**: Clear separation of bounded contexts and layers
- **SOLID Principles**: Single responsibility, Open/closed, etc.
- **DTO Pattern**: Separate domain models from API contracts
- **Validation**: Validate all input at the controller level
- **Exception Handling**: Use custom exceptions and global handler
- **Security**: Proper authentication and authorization

### Frontend Best Practices
- **Component-Based Architecture**: Reusable UI components
- **Type Safety**: TypeScript for all components and functions
- **State Management**: Proper state handling with hooks
- **Form Validation**: Client-side validation before submission
- **API Integration**: Centralized API client with error handling
- **Responsive Design**: Mobile-first approach

## Development Workflow
1. Run the backend server
2. Run the frontend development server
3. Access the application at http://localhost:3000
4. API documentation available at http://localhost:8080/swagger-ui.html
