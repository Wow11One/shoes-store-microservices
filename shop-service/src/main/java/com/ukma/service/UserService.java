package com.ukma.service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("authentication-service")
public class UserService {
}
