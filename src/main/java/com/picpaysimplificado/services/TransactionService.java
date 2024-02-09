package com.picpaysimplificado.services;

import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.exceptions.TransactionFraudException;
import com.picpaysimplificado.infra.security.SecurityFilter;
import com.picpaysimplificado.infra.security.TokenService;
import com.picpaysimplificado.models.transactions.TransactionModel;
import com.picpaysimplificado.models.users.UserModel;
import com.picpaysimplificado.repositories.TransactionRepository;
import com.picpaysimplificado.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;


    public TransactionModel createTransaction(TransactionDTO transaction, HttpServletRequest request) throws Exception {
        UserModel payer = this.userService.findUserById(transaction.payer_id());
        UserModel payee = this.userService.findUserById(transaction.payee_id());

        userService.validateTransaction(payer, transaction.value());

        boolean isAuthorized = this.authorizationService.authorizeTransaction(payer, transaction.value());
        if(!isAuthorized) {
            throw new Exception("Transação não autorizada");
        }

        UserDetails user = securityFilter.recoverLoggedUser(request.getHeader("Authorization"));
        if(user != null ) {
            if(!Objects.equals(user.getUsername(), payer.getUsername()) || payer.equals(payee)) {
                throw new TransactionFraudException();
            }
        }

        TransactionModel transactionModel = populateAndSaveTransaction(payer, payee, transaction.value());
        updateBalances(payer, payee, transaction.value());

        this.notificationService.sendNotification(payer, "Transação realizada com sucesso");
        this.notificationService.sendNotification(payee, "Transação recebida com sucesso");

        return transactionModel;
    }

    public TransactionModel populateAndSaveTransaction(UserModel payer, UserModel payee, BigDecimal value) {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setAmount(value);
        transactionModel.setPayer(payer);
        transactionModel.setPayee(payee);
        transactionModel.setTimestamp(LocalDateTime.now());

        return this.transactionRepository.save(transactionModel);
    }

    private void updateBalances(UserModel payer, UserModel payee, BigDecimal value) throws Exception {
        payer.setBalance(payer.getBalance().subtract(value));
        payee.setBalance(payee.getBalance().add(value));

        userService.saveUser(payer);
        userService.saveUser(payee);
    }

}
