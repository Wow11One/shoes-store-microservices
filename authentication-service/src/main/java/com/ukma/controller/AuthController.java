package com.ukma.controller;

import com.ukma.dto.AccountDto;
import com.ukma.dto.RegisterDto;
import com.ukma.service.AuthService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

@Controller("/api/auth")
public class AuthController {

    @Inject
    private AuthService authService;

    @Post("/register")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public AccountDto register(@Valid @Body RegisterDto registerDto) {
        return authService.register(registerDto);
    }

    @Get("/check")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public AccountDto check(Authentication authentication) {
        return new AccountDto(authentication.getName(), authentication.getRoles().stream().findFirst().get());
    }

    @Get("/users/{id}")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public AccountDto findUserById(Authentication authentication, @PathVariable("id") Long id) {
        return authService.findById(id);
    }
}
