package com.picpaysimplificado.dtos;

public record NotificationDTO(String email, String message) {
    @Override
    public String email() {
        return email;
    }

    @Override
    public String message() {
        return message;
    }
}
