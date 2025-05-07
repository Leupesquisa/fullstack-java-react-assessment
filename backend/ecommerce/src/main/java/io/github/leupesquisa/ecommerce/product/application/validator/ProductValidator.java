package io.github.leupesquisa.ecommerce.product.application.validator;

import io.github.leupesquisa.ecommerce.core.exception.DuplicateResourceException;
import io.github.leupesquisa.ecommerce.product.application.dto.ProductRequestDto;
import io.github.leupesquisa.ecommerce.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Validator for product data.
 * Performs complex validations beyond simple bean validation.
 */
@Component
@RequiredArgsConstructor
public class ProductValidator {

    private final ProductRepository productRepository;

    /**
     * Validate a product for creation.
     * Checks for SKU uniqueness.
     * 
     * @param requestDto the product data to validate
     * @throws DuplicateResourceException if a product with the same SKU already exists
     */
    public void validateForCreation(ProductRequestDto requestDto) {
        validateSkuUniqueness(requestDto.sku());
    }

    /**
     * Validate a product for update.
     * Checks for SKU uniqueness, excluding the current product.
     * 
     * @param id the ID of the product being updated
     * @param requestDto the product data to validate
     * @throws DuplicateResourceException if another product with the same SKU already exists
     */
    public void validateForUpdate(UUID id, ProductRequestDto requestDto) {
        validateSkuUniquenessExcludingProduct(requestDto.sku(), id);
    }

    /**
     * Validate that a SKU is unique.
     * 
     * @param sku the SKU to validate
     * @throws DuplicateResourceException if a product with the same SKU already exists
     */
    private void validateSkuUniqueness(String sku) {
        if (productRepository.existsBySku(sku)) {
            throw new DuplicateResourceException("Product", "sku", sku);
        }
    }

    /**
     * Validate that a SKU is unique, excluding a specific product.
     * 
     * @param sku the SKU to validate
     * @param productId the ID of the product to exclude from the check
     * @throws DuplicateResourceException if another product with the same SKU already exists
     */
    private void validateSkuUniquenessExcludingProduct(String sku, UUID productId) {
        if (productRepository.existsBySkuAndIdNot(sku, productId)) {
            throw new DuplicateResourceException("Product", "sku", sku);
        }
    }
}
