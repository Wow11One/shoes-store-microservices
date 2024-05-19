package com.ukma.service;

import com.ukma.dto.AccountDto;
import com.ukma.dto.RegisterDto;
import com.ukma.entity.User;
import com.ukma.entity.enums.UserRole;
import com.ukma.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class AuthService {


    private BCryptPasswordEncoderService passwordEncoderService;
    private UserRepository userRepository;

    @Inject
    public AuthService(BCryptPasswordEncoderService passwordEncoderService, UserRepository userRepository) {
        this.passwordEncoderService = passwordEncoderService;
        this.userRepository = userRepository;
    }

    public AccountDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return new AccountDto(user.getUsername(), user.getRole().toString());
    }

    public AccountDto register(RegisterDto registerDto) {
        String encodedPassword = passwordEncoderService.encode(registerDto.getPassword());
        User user = new User(registerDto.getUsername(), encodedPassword, UserRole.ADMIN);
        userRepository.save(user);

        return new AccountDto(registerDto.getUsername(), UserRole.USER.toString());
    }

}
