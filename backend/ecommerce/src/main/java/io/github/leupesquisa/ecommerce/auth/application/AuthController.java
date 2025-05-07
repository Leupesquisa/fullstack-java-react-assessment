package io.github.leupesquisa.ecommerce.auth.application;

import io.github.leupesquisa.ecommerce.auth.application.dto.AuthResponse;
import io.github.leupesquisa.ecommerce.auth.application.dto.UserLoginDto;
import io.github.leupesquisa.ecommerce.auth.application.dto.UserRegistrationDto;
import io.github.leupesquisa.ecommerce.auth.application.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for authentication operations.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Register a new user.
     *
     * @param registrationDto the user registration data
     * @return the authentication response with token and user information
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRegistrationDto registrationDto) {
        return ResponseEntity.ok(authService.register(registrationDto));
    }

    /**
     * Authenticate a user.
     *
     * @param loginDto the user login data
     * @return the authentication response with token and user information
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody UserLoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    /**
     * Get the current user information.
     *
     * @param userDetails the authenticated user details
     * @return the user information
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(authService.getUserInfo(userDetails.getUsername()));
    }
}