package com.picpaysimplificado.exceptions;

public class TransactionFraudException extends RuntimeException{

    public TransactionFraudException() {
        super("Operação não permitida");
    }

    public TransactionFraudException(String message){
        super(message);
    }

}
