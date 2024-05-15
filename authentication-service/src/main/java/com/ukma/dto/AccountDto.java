package com.ukma.dto;

import io.micronaut.serde.annotation.Serdeable;

import java.io.Serializable;

@Serdeable
public class AccountDto implements Serializable {

    private String email;
    private String role;


    public AccountDto(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
