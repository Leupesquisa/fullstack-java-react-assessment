package io.github.leupesquisa.ecommerce.product.infrastructure;

import io.github.leupesquisa.ecommerce.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * JPA repository for Product entities.
 * Provides methods for CRUD operations and custom queries.
 */
public interface ProductRepository extends JpaRepository<Product, UUID> {

    /**
     * Check if a product with the given SKU exists.
     * 
     * @param sku the SKU to check
     * @return true if a product with the SKU exists, false otherwise
     */
    boolean existsBySku(String sku);

    /**
     * Find a product by its SKU.
     * 
     * @param sku the SKU to search for
     * @return an Optional containing the product if found, empty otherwise
     */
    Optional<Product> findBySku(String sku);

    /**
     * Check if a product with the given SKU exists, excluding a specific product ID.
     * Useful for update operations to ensure SKU uniqueness except for the current product.
     * 
     * @param sku the SKU to check
     * @param id the product ID to exclude from the check
     * @return true if another product with the SKU exists, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Product p WHERE p.sku = :sku AND p.id != :id")
    boolean existsBySkuAndIdNot(@Param("sku") String sku, @Param("id") UUID id);
}
