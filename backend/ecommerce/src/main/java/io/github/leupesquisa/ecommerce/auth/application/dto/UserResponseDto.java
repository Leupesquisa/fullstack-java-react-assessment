package io.github.leupesquisa.ecommerce.auth.application.dto;

import io.github.leupesquisa.ecommerce.auth.domain.Role;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for user responses.
 * Contains user information without sensitive data like password.
 */
public record UserResponseDto(
    UUID id,
    String email,
    String firstName,
    String lastName,
    Role role,
    LocalDateTime createdAt
) {}