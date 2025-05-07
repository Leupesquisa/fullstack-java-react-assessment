package io.github.leupesquisa.ecommerce.event.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Event that is published when a product's stock changes.
 */
@Getter
public class ProductStockChangedEvent implements DomainEvent {
    /**
     * Enum representing the reason for a stock change.
     */
    public enum StockChangeReason {
        ORDER,          // Stock changed due to an order
        RETURN,         // Stock changed due to a return
        INVENTORY,      // Stock changed due to inventory adjustment
        SUPPLIER        // Stock changed due to supplier delivery
    }

    private final UUID productId;
    private final String productSku;
    private final String productName;
    private final int oldStock;
    private final int newStock;
    private final LocalDateTime timestamp;
    private final StockChangeReason reason;

    /**
     * Create a new ProductStockChangedEvent.
     *
     * @param productId the product ID
     * @param productSku the product SKU
     * @param productName the product name
     * @param oldStock the old stock value
     * @param newStock the new stock value
     * @param reason the reason for the stock change
     */
    public ProductStockChangedEvent(UUID productId, String productSku, String productName, int oldStock, int newStock, StockChangeReason reason) {
        this.productId = productId;
        this.productSku = productSku;
        this.productName = productName;
        this.oldStock = oldStock;
        this.newStock = newStock;
        this.reason = reason;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Create a new ProductStockChangedEvent with INVENTORY as the default reason.
     *
     * @param productId the product ID
     * @param productSku the product SKU
     * @param productName the product name
     * @param oldStock the old stock value
     * @param newStock the new stock value
     */
    public ProductStockChangedEvent(UUID productId, String productSku, String productName, int oldStock, int newStock) {
        this(productId, productSku, productName, oldStock, newStock, StockChangeReason.INVENTORY);
    }

    /**
     * Check if the stock has increased.
     *
     * @return true if the stock has increased, false otherwise
     */
    public boolean isStockIncreased() {
        return newStock > oldStock;
    }

    /**
     * Check if the stock has decreased.
     *
     * @return true if the stock has decreased, false otherwise
     */
    public boolean isStockDecreased() {
        return newStock < oldStock;
    }

    /**
     * Get the difference between the new and old stock.
     *
     * @return the stock difference
     */
    public int getStockDifference() {
        return newStock - oldStock;
    }
}
