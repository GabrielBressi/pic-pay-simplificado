package com.picpaysimplificado.dtos;

public record AuthDTO(String login, String password) {
    @Override
    public String login() {
        return login;
    }

    @Override
    public String password() {
        return password;
    }
}
