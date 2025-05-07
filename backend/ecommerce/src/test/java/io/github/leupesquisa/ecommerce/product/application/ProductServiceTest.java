package io.github.leupesquisa.ecommerce.product.application;

import io.github.leupesquisa.ecommerce.core.exception.ResourceNotFoundException;
import io.github.leupesquisa.ecommerce.event.application.EventPublisher;
import io.github.leupesquisa.ecommerce.event.domain.ProductStockChangedEvent;
import io.github.leupesquisa.ecommerce.product.application.dto.ProductRequestDto;
import io.github.leupesquisa.ecommerce.product.application.dto.ProductResponseDto;
import io.github.leupesquisa.ecommerce.product.application.mapper.ProductMapper;
import io.github.leupesquisa.ecommerce.product.domain.Product;
import io.github.leupesquisa.ecommerce.product.infrastructure.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ProductService.
 * Uses Mockito to mock dependencies and verify behavior.
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductRequestDto productRequestDto;
    private ProductResponseDto productResponseDto;
    private UUID productId;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        
        // Set up product entity
        product = new Product();
        product.setId(productId);
        product.setSku("TEST-SKU-123");
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(99.99));
        product.setStock(10);
        product.setCategory("Test Category");
        
        // Set up request DTO
        productRequestDto = new ProductRequestDto(
                "TEST-SKU-123",
                "Test Product",
                "Test Description",
                BigDecimal.valueOf(99.99),
                10,
                "Test Category",
                null
        );
        
        // Set up response DTO
        productResponseDto = new ProductResponseDto(
                productId,
                "TEST-SKU-123",
                "Test Product",
                "Test Description",
                BigDecimal.valueOf(99.99),
                10,
                "Test Category",
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void getAllProducts_ShouldReturnPageOfProductDtos() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        Page<Product> productPage = new PageImpl<>(List.of(product));
        when(productRepository.findAll(pageable)).thenReturn(productPage);
        when(productMapper.toResponseDto(product)).thenReturn(productResponseDto);

        // Act
        Page<ProductResponseDto> result = productService.getAllProducts(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(productId, result.getContent().get(0).id());
        verify(productRepository).findAll(pageable);
        verify(productMapper).toResponseDto(product);
    }

    @Test
    void getProductById_WithExistingId_ShouldReturnProduct() {
        // Arrange
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Product result = productService.getProductById(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Test Product", result.getName());
        verify(productRepository).findById(productId);
    }

    @Test
    void getProductDtoById_WithExistingId_ShouldReturnProductDto() {
        // Arrange
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toResponseDto(product)).thenReturn(productResponseDto);

        // Act
        ProductResponseDto result = productService.getProductDtoById(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.id());
        assertEquals("Test Product", result.name());
        verify(productRepository).findById(productId);
        verify(productMapper).toResponseDto(product);
    }

    @Test
    void getProductById_WithNonExistingId_ShouldThrowException() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById(nonExistingId);
        });
        verify(productRepository).findById(nonExistingId);
    }

    @Test
    void createProduct_ShouldSaveAndReturnProductDto() {
        // Arrange
        when(productMapper.toEntity(productRequestDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponseDto(product)).thenReturn(productResponseDto);

        // Act
        ProductResponseDto result = productService.createProduct(productRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.id());
        assertEquals("Test Product", result.name());
        verify(productMapper).toEntity(productRequestDto);
        verify(productRepository).save(product);
        verify(productMapper).toResponseDto(product);
    }

    @Test
    void updateProduct_WithExistingId_ShouldUpdateAndReturnProductDto() {
        // Arrange
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponseDto(product)).thenReturn(productResponseDto);

        // Act
        ProductResponseDto result = productService.updateProduct(productId, productRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.id());
        assertEquals("Test Product", result.name());
        verify(productRepository).findById(productId);
        verify(productMapper).updateEntityFromDto(productRequestDto, product);
        verify(productRepository).save(product);
        verify(productMapper).toResponseDto(product);
    }

    @Test
    void deleteProduct_WithExistingId_ShouldDeleteProduct() {
        // Arrange
        when(productRepository.existsById(productId)).thenReturn(true);

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository).existsById(productId);
        verify(productRepository).deleteById(productId);
    }

    @Test
    void deleteProduct_WithNonExistingId_ShouldThrowException() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(productRepository.existsById(nonExistingId)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteProduct(nonExistingId);
        });
        verify(productRepository).existsById(nonExistingId);
        verify(productRepository, never()).deleteById(any());
    }

    @Test
    void updateProductStock_ShouldUpdateStockAndReturnProductDto() {
        // Arrange
        int newStock = 5;
        ProductStockChangedEvent.StockChangeReason reason = ProductStockChangedEvent.StockChangeReason.ORDER;
        
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponseDto(product)).thenReturn(productResponseDto);

        // Act
        ProductResponseDto result = productService.updateProductStock(productId, newStock, reason);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.id());
        verify(productRepository).findById(productId);
        verify(productRepository).save(product);
        verify(eventPublisher).publish(any(ProductStockChangedEvent.class));
        verify(productMapper).toResponseDto(product);
    }
}