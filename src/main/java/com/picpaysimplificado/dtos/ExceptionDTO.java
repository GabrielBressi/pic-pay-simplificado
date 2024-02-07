package com.picpaysimplificado.dtos;

public record ExceptionDTO(String message, String statusCode) {
    @Override
    public String message() {
        return message;
    }

    @Override
    public String statusCode() {
        return statusCode;
    }
}
