package com.picpaysimplificado.services;

import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.models.transactions.TransactionModel;
import com.picpaysimplificado.models.users.UserModel;
import com.picpaysimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;


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

    public TransactionModel createTransaction(TransactionDTO transaction) throws Exception {
        UserModel payer = this.userService.findUserById(transaction.payer_id());
        UserModel payee = this.userService.findUserById(transaction.payee_id());

        userService.validateTransaction(payer, transaction.value());

        boolean isAuthorized = this.authorizationService.authorizeTransaction(payer, transaction.value());
        if(!isAuthorized) {
            throw new Exception("Transação não autorizada");
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
