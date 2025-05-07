package io.github.leupesquisa.ecommerce.auth.application;

import io.github.leupesquisa.ecommerce.auth.application.dto.AuthResponse;
import io.github.leupesquisa.ecommerce.auth.application.dto.UserLoginDto;
import io.github.leupesquisa.ecommerce.auth.application.dto.UserRegistrationDto;
import io.github.leupesquisa.ecommerce.auth.application.dto.UserResponseDto;
import io.github.leupesquisa.ecommerce.auth.application.mapper.UserMapper;
import io.github.leupesquisa.ecommerce.auth.domain.Role;
import io.github.leupesquisa.ecommerce.auth.domain.User;
import io.github.leupesquisa.ecommerce.auth.infrastructure.UserRepository;
import io.github.leupesquisa.ecommerce.core.config.JwtService;
import io.github.leupesquisa.ecommerce.core.exception.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for authentication operations.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Register a new user.
     *
     * @param registrationDto the user registration data
     * @return the authentication response with token and user information
     * @throws DuplicateResourceException if a user with the same email already exists
     */
    @Transactional
    public AuthResponse register(UserRegistrationDto registrationDto) {
        // Check if user already exists
        if (userRepository.existsByEmail(registrationDto.email())) {
            throw new DuplicateResourceException("User", "email", registrationDto.email());
        }

        // Create new user
        User user = userMapper.toEntity(registrationDto);
        user.setPassword(passwordEncoder.encode(registrationDto.password()));
        user.setRole(Role.ROLE_USER); // Default role for new users

        // Save user
        User savedUser = userRepository.save(user);

        // Generate token
        String token = jwtService.generateToken(savedUser);

        // Return response
        return new AuthResponse(token, userMapper.toResponseDto(savedUser));
    }

    /**
     * Authenticate a user.
     *
     * @param loginDto the user login data
     * @return the authentication response with token and user information
     * @throws UsernameNotFoundException if the user is not found
     */
    public AuthResponse login(UserLoginDto loginDto) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.email(),
                        loginDto.password()
                )
        );

        // Get user
        User user = userRepository.findByEmail(loginDto.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginDto.email()));

        // Generate token
        String token = jwtService.generateToken(user);

        // Return response
        return new AuthResponse(token, userMapper.toResponseDto(user));
    }

    /**
     * Get user information.
     *
     * @param email the user email
     * @return the user information
     * @throws UsernameNotFoundException if the user is not found
     */
    public UserResponseDto getUserInfo(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return userMapper.toResponseDto(user);
    }
}