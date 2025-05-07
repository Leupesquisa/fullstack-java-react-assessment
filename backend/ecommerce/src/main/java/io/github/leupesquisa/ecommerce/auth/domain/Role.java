package io.github.leupesquisa.ecommerce.auth.domain;

/**
 * Enum representing user roles in the system.
 * Used for role-based access control.
 */
public enum Role {
    ROLE_USER,    // Regular user with read-only access
    ROLE_ADMIN    // Administrator with full access
}