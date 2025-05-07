package io.github.leupesquisa.ecommerce.event.domain;

import java.time.LocalDateTime;

/**
 * Marker interface for domain events.
 * All domain events should implement this interface.
 */
public interface DomainEvent {
    
    /**
     * Get the timestamp when the event occurred.
     * 
     * @return the event timestamp
     */
    LocalDateTime getTimestamp();
}