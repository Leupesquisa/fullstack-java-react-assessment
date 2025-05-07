package io.github.leupesquisa.ecommerce.product.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for product responses.
 * Uses Java record for immutability and automatic getter/equals/hashCode/toString generation.
 */
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