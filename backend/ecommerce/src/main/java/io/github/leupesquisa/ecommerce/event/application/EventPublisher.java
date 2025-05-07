package io.github.leupesquisa.ecommerce.event.application;

import io.github.leupesquisa.ecommerce.event.domain.DomainEvent;

/**
 * Interface for publishing domain events.
 */
public interface EventPublisher {
    
    /**
     * Publish a domain event.
     * 
     * @param event the event to publish
     */
    void publish(DomainEvent event);
}