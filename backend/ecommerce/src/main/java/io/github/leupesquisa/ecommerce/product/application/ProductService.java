package io.github.leupesquisa.ecommerce.product.application;

import io.github.leupesquisa.ecommerce.core.exception.ResourceNotFoundException;
import io.github.leupesquisa.ecommerce.event.application.EventPublisher;
import io.github.leupesquisa.ecommerce.event.domain.ProductStockChangedEvent;
import io.github.leupesquisa.ecommerce.product.application.dto.ProductRequestDto;
import io.github.leupesquisa.ecommerce.product.application.dto.ProductResponseDto;
import io.github.leupesquisa.ecommerce.product.application.mapper.ProductMapper;
import io.github.leupesquisa.ecommerce.product.application.validator.ProductValidator;
import io.github.leupesquisa.ecommerce.product.domain.Product;
import io.github.leupesquisa.ecommerce.product.domain.exception.ProductNotFoundException;
import io.github.leupesquisa.ecommerce.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Service for product operations.
 * Implements business logic for CRUD operations on products.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductValidator productValidator;
    private final EventPublisher eventPublisher;

    /**
     * Get all products with pagination.
     *
     * @param pageable the pagination information
     * @return a page of product DTOs
     */
    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toResponseDto);
    }

    /**
     * Get a product DTO by ID.
     *
     * @param id the product ID
     * @return the product DTO
     * @throws ProductNotFoundException if the product is not found
     */
    public ProductResponseDto getProductDtoById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toResponseDto(product);
    }

    /**
     * Get a product entity by ID.
     *
     * @param id the product ID
     * @return the product entity
     * @throws ProductNotFoundException if the product is not found
     */
    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * Create a new product.
     *
     * @param requestDto the product creation data
     * @return the created product DTO
     */
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        productValidator.validateForCreation(requestDto);

        Product product = productMapper.toEntity(requestDto);
        Product savedProduct = productRepository.save(product);

        return productMapper.toResponseDto(savedProduct);
    }

    /**
     * Update an existing product.
     *
     * @param id the product ID
     * @param requestDto the product update data
     * @return the updated product DTO
     * @throws ProductNotFoundException if the product is not found
     */
    @Transactional
    public ProductResponseDto updateProduct(UUID id, ProductRequestDto requestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productValidator.validateForUpdate(id, requestDto);

        productMapper.updateEntityFromDto(requestDto, product);
        Product updatedProduct = productRepository.save(product);

        return productMapper.toResponseDto(updatedProduct);
    }

    /**
     * Delete a product.
     *
     * @param id the product ID
     * @throws ProductNotFoundException if the product is not found
     */
    @Transactional
    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

    /**
     * Update the stock of a product.
     *
     * @param id the product ID
     * @param newStock the new stock value
     * @param reason the reason for the stock change
     * @return the updated product DTO
     * @throws ProductNotFoundException if the product is not found
     */
    @Transactional
    public ProductResponseDto updateProductStock(UUID id, int newStock, ProductStockChangedEvent.StockChangeReason reason) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        int oldStock = product.getStock();
        product.setStock(newStock);
        Product updatedProduct = productRepository.save(product);

        // Publish event
        ProductStockChangedEvent event = new ProductStockChangedEvent(
                product.getId(),
                product.getSku(),
                product.getName(),
                oldStock,
                newStock,
                reason
        );
        eventPublisher.publish(event);

        return productMapper.toResponseDto(updatedProduct);
    }
}
