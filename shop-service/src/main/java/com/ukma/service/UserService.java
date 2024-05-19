package com.ukma.service;

import com.ukma.dto.user.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("authentication-service")
@Service
public interface UserService {

    @GetMapping("/api/auth/users/{email}")
    UserDto findUserByEmail(@PathVariable("email") String email);
}
