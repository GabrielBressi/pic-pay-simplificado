package com.picpaysimplificado.infra;

import com.picpaysimplificado.dtos.ExceptionDTO;
import com.picpaysimplificado.exceptions.UserShopKeeperTransactionException;
import com.picpaysimplificado.exceptions.UserWithNoBalanceException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDTO> threatDuplicateEntry(DataIntegrityViolationException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Usuário já cadastrado", "400");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> threat404(DataIntegrityViolationException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> threatGeneralExceptions(Exception exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "500");
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }

    @ExceptionHandler(UserShopKeeperTransactionException.class)
    public ResponseEntity<ExceptionDTO> userShopKeeperTransactionHandler(UserShopKeeperTransactionException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Usuário do tipo logista não está autorizado a realizar transação.", "400");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDTO);
    }

    @ExceptionHandler(UserWithNoBalanceException.class)
    public ResponseEntity<ExceptionDTO> userWithNoBalanceHandler(UserWithNoBalanceException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Saldo insuficiente", "400");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDTO);
    }

}
