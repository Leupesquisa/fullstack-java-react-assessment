package io.github.leupesquisa.ecommerce.auth.infrastructure;

import io.github.leupesquisa.ecommerce.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * JPA repository for User entities.
 * Provides methods for CRUD operations and custom queries.
 */
public interface UserRepository extends JpaRepository<User, UUID> {
    
    /**
     * Find a user by email.
     * 
     * @param email the email to search for
     * @return an Optional containing the user if found, empty otherwise
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check if a user with the given email exists.
     * 
     * @param email the email to check
     * @return true if a user with the email exists, false otherwise
     */
    boolean existsByEmail(String email);
}