package io.github.leupesquisa.ecommerce.auth.application.mapper;

import io.github.leupesquisa.ecommerce.auth.application.dto.UserRegistrationDto;
import io.github.leupesquisa.ecommerce.auth.application.dto.UserResponseDto;
import io.github.leupesquisa.ecommerce.auth.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for converting between User entity and DTOs.
 * The implementation will be generated at compile time.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convert entity to response DTO
     */
    UserResponseDto toResponseDto(User user);

    /**
     * Convert registration DTO to entity
     * Password will be encoded by the service before saving
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserRegistrationDto registrationDto);
}