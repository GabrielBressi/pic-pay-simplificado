package com.picpaysimplificado.services;

import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.models.users.UserModel;
import com.picpaysimplificado.models.users.UserType;
import com.picpaysimplificado.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private AuthorizationService authorizationService;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create transaction successfully when everything is okay.")
    void createTransactionCase1() throws Exception {
        UserModel payer = new UserModel(
                1L,
                "Joao",
                "Silva",
                "99999999901",
                "joao@gmail.com",
                "12345678",
                new BigDecimal(10),
                UserType.COMMON
        );

        UserModel payee = new UserModel(
                2L,
                "Maria",
                "Santos",
                "99999999902",
                "maria@gmail.com",
                "12345678",
                new BigDecimal(10),
                UserType.COMMON
        );

        when(userService.findUserById(1L)).thenReturn(payer);
        when(userService.findUserById(2L)).thenReturn(payee);

        when(authorizationService.authorizeTransaction(any(), any())).thenReturn(true);

        TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);
        transactionService.createTransaction(request);

        verify(transactionRepository, times(1)).save(any());

        payer.setBalance(new BigDecimal(0));
        verify(userService, times(1)).saveUser(payer);

        payee.setBalance(new BigDecimal(20));
        verify(userService, times(1)).saveUser(payee);

        verify(notificationService, times(1)).sendNotification(payer, "Transação realizada com sucesso");
        verify(notificationService, times(1)).sendNotification(payee, "Transação recebida com sucesso");
    }

    @Test
    @DisplayName("Should throw Exception when transaction is not allowed.")
    void createTransactionCase2() throws Exception{
        UserModel payer = new UserModel(
                1L,
                "Joao",
                "Silva",
                "99999999901",
                "joao@gmail.com",
                "12345678",
                new BigDecimal(10),
                UserType.COMMON
        );

        UserModel payee = new UserModel(
                2L,
                "Maria",
                "Santos",
                "99999999902",
                "maria@gmail.com",
                "12345678",
                new BigDecimal(10),
                UserType.COMMON
        );

        when(userService.findUserById(1L)).thenReturn(payer);
        when(userService.findUserById(2L)).thenReturn(payee);

        when(authorizationService.authorizeTransaction(any(), any())).thenReturn(false);

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);
            transactionService.createTransaction(request);
        });

        Assertions.assertEquals("Transação não autorizada", thrown.getMessage());
    }

//    @Test
//    void authorizeTransaction() {
//    }
}