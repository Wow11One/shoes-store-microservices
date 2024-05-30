package com.ukma.authentication.provider;

import com.ukma.entity.User;
import com.ukma.repository.UserRepository;
import com.ukma.service.BCryptPasswordEncoderService;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.AuthenticationProvider;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Inject
    private UserRepository userRepository;

    @Inject
    private BCryptPasswordEncoderService passwordEncoderService;

    @Override
    public AuthenticationResponse authenticate(Object requestContext,
                                               @NonNull AuthenticationRequest authRequest) {

        String username = (String) authRequest.getIdentity();
        String password = (String) authRequest.getSecret();
        User user = userRepository.findByUsername(username).orElse(null);


        if (user == null || !passwordEncoderService.matches(password, user.getPassword())) {
            throw new AuthenticationException(new AuthenticationFailed());
        }

        return AuthenticationResponse.success(username, List.of(user.getRole().toString()));
    }
}
