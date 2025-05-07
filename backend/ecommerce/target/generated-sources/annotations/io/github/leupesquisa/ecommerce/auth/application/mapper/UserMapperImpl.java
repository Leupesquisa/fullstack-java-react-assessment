package io.github.leupesquisa.ecommerce.auth.application.mapper;

import io.github.leupesquisa.ecommerce.auth.application.dto.UserRegistrationDto;
import io.github.leupesquisa.ecommerce.auth.application.dto.UserResponseDto;
import io.github.leupesquisa.ecommerce.auth.domain.Role;
import io.github.leupesquisa.ecommerce.auth.domain.User;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-07T21:05:53+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDto toResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        UUID id = null;
        String email = null;
        String firstName = null;
        String lastName = null;
        Role role = null;
        LocalDateTime createdAt = null;

        id = user.getId();
        email = user.getEmail();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        role = user.getRole();
        createdAt = user.getCreatedAt();

        UserResponseDto userResponseDto = new UserResponseDto( id, email, firstName, lastName, role, createdAt );

        return userResponseDto;
    }

    @Override
    public User toEntity(UserRegistrationDto registrationDto) {
        if ( registrationDto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( registrationDto.email() );
        user.setPassword( registrationDto.password() );
        user.setFirstName( registrationDto.firstName() );
        user.setLastName( registrationDto.lastName() );

        return user;
    }
}
