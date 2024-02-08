package com.picpaysimplificado.exceptions;

public class UserShopKeeperTransactionException extends RuntimeException{

    public UserShopKeeperTransactionException() {
        super("Usuário do tipo logista não está autorizado a realizar transação.");
    }

    public UserShopKeeperTransactionException(String message) {
        super(message);
    }

}
