package io.github.leupesquisa.ecommerce.product.domain.exception;

import io.github.leupesquisa.ecommerce.core.exception.ResourceNotFoundException;

import java.util.UUID;

/**
 * Exception thrown when a requested product is not found.
 * Extends the generic ResourceNotFoundException.
 */
public class ProductNotFoundException extends ResourceNotFoundException {

    public ProductNotFoundException(UUID id) {
        super("Product", "id", id);
    }

    public ProductNotFoundException(String sku) {
        super("Product", "sku", sku);
    }
}