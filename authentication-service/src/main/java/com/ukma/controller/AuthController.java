package com.ukma.controller;

import com.ukma.dto.AccountDto;
import com.ukma.dto.RegisterDto;
import com.ukma.service.AuthService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Options;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.server.cors.CorsFilter;
import io.micronaut.http.server.cors.CrossOrigin;
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
    public MutableHttpResponse<AccountDto> check(Authentication authentication) {

        return HttpResponse.ok(
                new AccountDto(authentication.getName(), authentication.getRoles().stream().findFirst().get())
        );
    }

    @Options("/check")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public MutableHttpResponse<?> checkOptions() {
        return HttpResponse.ok();
    }

    @Options("/login")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public MutableHttpResponse<?> loginOptions() {
        return HttpResponse.ok();
    }

    @Options("/register")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public MutableHttpResponse<?> registerOptions() {
        return HttpResponse.ok();
    }

    @Get("/users/{email}")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public AccountDto findUserByEmail(@PathVariable("email") String email) {
        return authService.findByUsername(email);
    }
}
