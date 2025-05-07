# Coding Guidelines for E-commerce Monolith

This document outlines the coding conventions and best practices for the E-commerce Monolith project.

## General Principles

1. **Follow DDD principles**: Respect bounded contexts and maintain clear separation of concerns.
2. **SOLID principles**: Apply Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, and Dependency Inversion principles.
3. **Clean Code**: Write readable, maintainable code with meaningful names and appropriate comments.
4. **Test-Driven Development**: Write tests before implementing features when possible.

## Project Structure

### Backend

```
com.ecommerce/
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
    └── util/
```

### Frontend

```
src/
├── app/
│   ├── api/
│   ├── auth/
│   ├── products/
│   ├── cart/
│   └── orders/
├── components/
│   ├── atoms/
│   ├── molecules/
│   ├── organisms/
│   └── templates/
├── lib/
├── types/
└── styles/
```

## Java Conventions

### DTOs

1. **Use Java Records**: For all DTOs to leverage immutability and automatic getter/equals/hashCode/toString generation.

```java
public record ProductRequestDto(
    @NotBlank String sku,
    @NotBlank String name,
    BigDecimal price,
    Integer stock
) {}
```

2. **Validation**: Include Jakarta validation annotations on DTO fields.

### Entities

1. **JPA Entities**: Use Lombok `@Getter`, `@Setter` annotations for entities.
2. **ID Generation**: Use UUID as the primary key type with `@GeneratedValue(strategy = GenerationType.UUID)`.
3. **Auditing**: Extend the `Auditable` base class for entities that need audit fields.

```java
@Entity
@Table(name = "products", schema = "ecommerce")
@Getter
@Setter
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    // Other fields
}
```

### Services

1. **Transactional**: Use `@Transactional` annotations appropriately.
2. **Constructor Injection**: Use constructor injection with `@RequiredArgsConstructor` for dependency injection.

```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    
    @Transactional
    public Product createProduct(Product product) {
        // Implementation
    }
}
```

### Mappers

1. **MapStruct**: Use MapStruct for object mapping between entities and DTOs.
2. **Component Model**: Use Spring component model for mappers.

```java
@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDto toResponseDto(Product product);
    Product toEntity(ProductRequestDto requestDto);
}
```

### Controllers

1. **REST Conventions**: Follow REST conventions for endpoint design.
2. **Response Entity**: Use `ResponseEntity` for HTTP responses.
3. **Validation**: Use `@Valid` annotation for request validation.

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
    // Implementation
    
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto) {
        // Implementation
    }
}
```

### Exception Handling

1. **Global Exception Handler**: Use a global exception handler for consistent error responses.
2. **Custom Exceptions**: Create specific exception classes for different error scenarios.

```java
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
```

## TypeScript Conventions

### Types

1. **Define Interfaces**: Create TypeScript interfaces for all data structures.
2. **Export Types**: Export types from dedicated type files.

```typescript
export interface Product {
  id: string;
  sku: string;
  name: string;
  price: number;
  stock: number;
  category?: string;
  imageUrl?: string;
}
```

### Components

1. **Functional Components**: Use functional components with hooks.
2. **Props Typing**: Always type component props.

```typescript
interface ProductCardProps {
  product: Product;
  onAddToCart: (productId: string) => void;
}

export const ProductCard: React.FC<ProductCardProps> = ({ product, onAddToCart }) => {
  // Implementation
};
```

### API Calls

1. **Centralized API Client**: Use a centralized API client for all backend communication.
2. **Error Handling**: Implement proper error handling for API calls.

```typescript
export const fetchProduct = async (id: string): Promise<Product> => {
  try {
    const response = await apiClient.get(`/api/products/${id}`);
    return response.data;
  } catch (error) {
    // Error handling
    throw error;
  }
};
```

## Testing Guidelines

### Backend Testing

1. **Unit Tests**: Write unit tests for services and domain logic.
2. **Integration Tests**: Write integration tests for controllers and repositories.
3. **Test Naming**: Use descriptive test names following the pattern `should_ExpectedBehavior_When_StateUnderTest`.

```java
@Test
void should_ReturnProduct_When_ProductExists() {
    // Test implementation
}
```

### Frontend Testing

1. **Component Tests**: Test components in isolation.
2. **E2E Tests**: Write Cypress tests for critical user flows.
3. **Mock API Calls**: Use MSW or similar for mocking API calls in tests.

## Code Review Checklist

- [ ] Code follows the project structure and conventions
- [ ] All new code has appropriate tests
- [ ] No unnecessary comments or debug code
- [ ] Error handling is implemented
- [ ] Security considerations are addressed
- [ ] Performance implications are considered
- [ ] Documentation is updated if necessary