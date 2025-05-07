package io.github.leupesquisa.ecommerce.event.application;

import io.github.leupesquisa.ecommerce.event.domain.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Implementation of EventPublisher that uses Spring's ApplicationEventPublisher.
 */
@Component
@RequiredArgsConstructor
public class SpringEventPublisher implements EventPublisher {
    
    private final ApplicationEventPublisher applicationEventPublisher;
    
    @Override
    public void publish(DomainEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}