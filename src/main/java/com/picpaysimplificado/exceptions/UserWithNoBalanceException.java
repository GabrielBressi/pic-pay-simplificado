package com.picpaysimplificado.exceptions;

public class UserWithNoBalanceException extends RuntimeException{

    public UserWithNoBalanceException() {
        super("Saldo insuficiente");
    }

    public UserWithNoBalanceException(String message) {
        super(message);
    }
}
