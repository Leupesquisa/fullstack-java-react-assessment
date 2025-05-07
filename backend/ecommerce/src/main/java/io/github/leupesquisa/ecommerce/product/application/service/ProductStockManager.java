package io.github.leupesquisa.ecommerce.product.application.service;

import io.github.leupesquisa.ecommerce.event.application.EventPublisher;
import io.github.leupesquisa.ecommerce.event.domain.ProductStockChangedEvent;
import io.github.leupesquisa.ecommerce.product.domain.Product;
import io.github.leupesquisa.ecommerce.product.domain.exception.ProductNotFoundException;
import io.github.leupesquisa.ecommerce.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service for managing product stock.
 */
@Service
@RequiredArgsConstructor
public class ProductStockManager {

    private final ProductRepository productRepository;
    private final EventPublisher eventPublisher;

    /**
     * Update the stock of a product.
     *
     * @param productId the product ID
     * @param newStock the new stock value
     * @return the updated product
     * @throws ProductNotFoundException if the product is not found
     */
    @Transactional
    public Product updateStock(UUID productId, int newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        int oldStock = product.getStock();
        product.setStock(newStock);
        Product updatedProduct = productRepository.save(product);

        // Publish event
        ProductStockChangedEvent event = new ProductStockChangedEvent(
                product.getId(),
                product.getSku(),
                product.getName(),
                oldStock,
                newStock
        );
        eventPublisher.publish(event);

        return updatedProduct;
    }

    /**
     * Increase the stock of a product.
     *
     * @param productId the product ID
     * @param quantity the quantity to add
     * @return the updated product
     * @throws ProductNotFoundException if the product is not found
     * @throws IllegalArgumentException if the quantity is negative
     */
    @Transactional
    public Product increaseStock(UUID productId, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity to increase must be positive");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        return updateStock(productId, product.getStock() + quantity);
    }

    /**
     * Decrease the stock of a product.
     *
     * @param productId the product ID
     * @param quantity the quantity to subtract
     * @return the updated product
     * @throws ProductNotFoundException if the product is not found
     * @throws IllegalArgumentException if the quantity is negative or greater than the current stock
     */
    @Transactional
    public Product decreaseStock(UUID productId, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity to decrease must be positive");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        return updateStock(productId, product.getStock() - quantity);
    }
}