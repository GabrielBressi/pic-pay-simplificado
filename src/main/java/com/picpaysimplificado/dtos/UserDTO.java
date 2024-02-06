package com.picpaysimplificado.dtos;

import com.picpaysimplificado.models.users.UserType;

import java.math.BigDecimal;

public record UserDTO(String fistname, String lastname, String document, BigDecimal balance, String email, String password, UserType userType) {
    @Override
    public String fistname() {
        return fistname;
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
    public UserType userType() {
        return userType;
    }
}
