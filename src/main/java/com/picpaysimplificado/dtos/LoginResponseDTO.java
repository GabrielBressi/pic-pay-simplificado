package com.picpaysimplificado.dtos;

public record LoginResponseDTO(String token) {
    @Override
    public String token() {
        return token;
    }
}
