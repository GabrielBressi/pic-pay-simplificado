package com.picpaysimplificado.services;

import com.picpaysimplificado.models.users.UserModel;
import com.picpaysimplificado.models.users.UserType;
import com.picpaysimplificado.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should throw exception if UserType is SHOPKEEPER")
    void validateTransactionCase1() throws Exception {
        UserModel payer = new UserModel(
                1L,
                "Joao",
                "Silva",
                "99999999901",
                "joao@gmail.com",
                "12345678",
                new BigDecimal(10),
                UserType.SHOPKEEPER
        );

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
           userService.validateTransaction(payer, new BigDecimal(10));
        });

        Assertions.assertEquals("Usuário do tipo logista não está autorizado a realizar transação.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when User is with insufficient balance")
    void validateTransactionCase2() throws Exception {
        UserModel payer = new UserModel(
                1L,
                "Joao",
                "Silva",
                "99999999901",
                "joao@gmail.com",
                "12345678",
                new BigDecimal(0),
                UserType.COMMON
        );

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            userService.validateTransaction(payer, new BigDecimal(10));
        });

        Assertions.assertEquals("Saldo insuficiente", thrown.getMessage());
    }
    
}