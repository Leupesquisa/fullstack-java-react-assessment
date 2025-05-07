package io.github.leupesquisa.ecommerce.product.application.mapper;

import io.github.leupesquisa.ecommerce.product.application.dto.ProductRequestDto;
import io.github.leupesquisa.ecommerce.product.application.dto.ProductResponseDto;
import io.github.leupesquisa.ecommerce.product.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for converting between Product entity and DTOs.
 * The implementation will be generated at compile time.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    /**
     * Convert entity to response DTO
     */
    ProductResponseDto toResponseDto(Product product);

    /**
     * Convert request DTO to entity (for creation)
     */
    Product toEntity(ProductRequestDto requestDto);

    /**
     * Update existing entity from request DTO
     */
    void updateEntityFromDto(ProductRequestDto requestDto, @MappingTarget Product product);
}