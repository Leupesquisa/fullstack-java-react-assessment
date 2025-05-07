package io.github.leupesquisa.ecommerce.product.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * DTO for product creation and update requests.
 * Uses Java record for immutability and automatic getter/equals/hashCode/toString generation.
 */
public record ProductRequestDto(
        @NotBlank(message = "SKU is required")
        @Size(min = 3, max = 50, message = "SKU must be between 3 and 50 characters")
        String sku,
        
        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
        String name,
        
        @Size(max = 1000, message = "Description cannot exceed 1000 characters")
        String description,
        
        @NotNull(message = "Price is required")
        @Min(value = 0, message = "Price must be greater than or equal to 0")
        BigDecimal price,
        
        @NotNull(message = "Stock is required")
        @Min(value = 0, message = "Stock must be greater than or equal to 0")
        Integer stock,
        
        String category,
        
        String imageUrl
) {}