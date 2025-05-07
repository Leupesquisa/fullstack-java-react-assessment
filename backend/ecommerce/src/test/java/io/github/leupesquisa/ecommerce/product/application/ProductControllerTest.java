package io.github.leupesquisa.ecommerce.product.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.leupesquisa.ecommerce.core.exception.ResourceNotFoundException;
import io.github.leupesquisa.ecommerce.product.application.dto.ProductRequestDto;
import io.github.leupesquisa.ecommerce.product.application.dto.ProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for ProductController.
 * Uses MockMvc to simulate HTTP requests and verify responses.
 */
@WebMvcTest(ProductController.class)
@Import(ProductControllerTest.TestConfig.class)
class ProductControllerTest {

    @Configuration
    static class TestConfig {
        @Bean
        public ProductService productService() {
            return mock(ProductService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;

    private ProductResponseDto productResponseDto;
    private ProductRequestDto productRequestDto;
    private UUID productId;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();

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
    }

    @Test
    void getAllProducts_ShouldReturnPageOfProducts() throws Exception {
        // Arrange
        Page<ProductResponseDto> dtoPage = new PageImpl<>(List.of(productResponseDto));
        when(productService.getAllProducts(any(Pageable.class))).thenReturn(dtoPage);

        // Act & Assert
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id", is(productId.toString())))
                .andExpect(jsonPath("$.content[0].name", is("Test Product")));

        verify(productService).getAllProducts(any(Pageable.class));
    }

    @Test
    void getProductById_WithExistingId_ShouldReturnProduct() throws Exception {
        // Arrange
        when(productService.getProductDtoById(productId)).thenReturn(productResponseDto);

        // Act & Assert
        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(productId.toString())))
                .andExpect(jsonPath("$.name", is("Test Product")));

        verify(productService).getProductDtoById(productId);
    }

    @Test
    void getProductById_WithNonExistingId_ShouldReturnNotFound() throws Exception {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(productService.getProductDtoById(nonExistingId))
                .thenThrow(new ResourceNotFoundException("Product", "id", nonExistingId));

        // Act & Assert
        mockMvc.perform(get("/api/products/{id}", nonExistingId))
                .andExpect(status().isNotFound());

        verify(productService).getProductDtoById(nonExistingId);
    }

    @Test
    void createProduct_WithValidData_ShouldReturnCreatedProduct() throws Exception {
        // Arrange
        when(productService.createProduct(any(ProductRequestDto.class))).thenReturn(productResponseDto);

        // Act & Assert
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id", is(productId.toString())))
                .andExpect(jsonPath("$.name", is("Test Product")));

        verify(productService).createProduct(any(ProductRequestDto.class));
    }

    @Test
    void updateProduct_WithValidData_ShouldReturnUpdatedProduct() throws Exception {
        // Arrange
        when(productService.updateProduct(eq(productId), any(ProductRequestDto.class))).thenReturn(productResponseDto);

        // Act & Assert
        mockMvc.perform(put("/api/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(productId.toString())))
                .andExpect(jsonPath("$.name", is("Test Product")));

        verify(productService).updateProduct(eq(productId), any(ProductRequestDto.class));
    }

    @Test
    void deleteProduct_WithExistingId_ShouldReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(productService).deleteProduct(productId);

        // Act & Assert
        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(productId);
    }
}