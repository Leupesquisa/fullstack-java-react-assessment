package io.github.leupesquisa.ecommerce.product.application;

import io.github.leupesquisa.ecommerce.product.application.dto.ProductRequestDto;
import io.github.leupesquisa.ecommerce.product.application.dto.ProductResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

/**
 * REST controller for product operations.
 * Uses DTOs for input and output to ensure proper validation and encapsulation.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get all products with pagination.
     *
     * @param pageable the pagination information
     * @return a page of product DTOs
     */
    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(Pageable pageable) {
        Page<ProductResponseDto> productDtos = productService.getAllProducts(pageable);
        return ResponseEntity.ok(productDtos);
    }

    /**
     * Get a product by ID.
     *
     * @param id the product ID
     * @return the product DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id) {
        ProductResponseDto productDto = productService.getProductDtoById(id);
        return ResponseEntity.ok(productDto);
    }

    /**
     * Create a new product.
     *
     * @param requestDto the product creation data
     * @return the created product DTO
     */
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto) {
        ProductResponseDto savedProductDto = productService.createProduct(requestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProductDto.id())
                .toUri();

        return ResponseEntity.created(location)
                .body(savedProductDto);
    }

    /**
     * Update an existing product.
     *
     * @param id the product ID
     * @param requestDto the product update data
     * @return the updated product DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable UUID id, 
            @Valid @RequestBody ProductRequestDto requestDto) {

        ProductResponseDto updatedProductDto = productService.updateProduct(id, requestDto);
        return ResponseEntity.ok(updatedProductDto);
    }

    /**
     * Delete a product.
     *
     * @param id the product ID
     * @return no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
