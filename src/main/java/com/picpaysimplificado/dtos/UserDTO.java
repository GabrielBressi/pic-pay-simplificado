package com.picpaysimplificado.dtos;

import com.picpaysimplificado.models.users.UserRole;

import java.math.BigDecimal;

public record UserDTO(String firstname, String lastname, String document, BigDecimal balance, String email, String password, UserRole role) {

    @Override
    public String firstname() {
        return firstname;
    }

    @Override
    public String lastname() {
        return lastname;
    }

    @Override
    public String document() {
        return document;
    }

    @Override
    public BigDecimal balance() {
        return balance;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String password() {
        return password;
    }
    @Override
    public UserRole role() {
        return role;
    }
}
