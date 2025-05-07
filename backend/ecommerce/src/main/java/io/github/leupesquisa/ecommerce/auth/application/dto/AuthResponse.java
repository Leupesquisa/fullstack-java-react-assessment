package io.github.leupesquisa.ecommerce.auth.application.dto;

/**
 * DTO for authentication responses.
 * Contains the JWT token and user information.
 */
public record AuthResponse(
    String token,
    UserResponseDto user
) {}