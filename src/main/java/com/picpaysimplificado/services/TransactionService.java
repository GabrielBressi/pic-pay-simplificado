package com.picpaysimplificado.services;

import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.models.transactions.TransactionModel;
import com.picpaysimplificado.models.users.UserModel;
import com.picpaysimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import static com.picpaysimplificado.constants.AppConstants.*;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    @Value("${transaction.checker}")
    private String transactionChecker;


    public TransactionModel createTransaction(TransactionDTO transaction) throws Exception {
        UserModel payer = this.userService.findUserById(transaction.payer_id());
        UserModel payee = this.userService.findUserById(transaction.payee_id());

        userService.validateTransaction(payer, transaction.value());

        boolean isAuthorized = this.authorizeTransaction(payer, transaction.value());
        if(!isAuthorized) {
            throw new Exception("Transação não autorizada");
        }

        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setAmount(transaction.value());
        transactionModel.setPayer(payer);
        transactionModel.setPayee(payee);
        transactionModel.setTimestamp(LocalDateTime.now());

        payer.setBalance(payer.getBalance().subtract(transaction.value()));
        payee.setBalance(payee.getBalance().add(transaction.value()));

        this.transactionRepository.save(transactionModel);
        this.userService.saveUser(payer);
        this.userService.saveUser(payee);

        this.notificationService.sendNotification(payer, "Transação realizada com sucesso");
        this.notificationService.sendNotification(payee, "Transação recebida com sucesso");

        return transactionModel;
    }

    public boolean authorizeTransaction(UserModel user, BigDecimal value) {
      ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(transactionChecker, Map.class);

      if(authorizationResponse.getStatusCode() == HttpStatus.OK) {
          String message = (String) authorizationResponse.getBody().get(MESSAGE) != null ? (String) authorizationResponse.getBody().get(MESSAGE) : EMPTY;
          return authorizationResponse.getBody().get(MESSAGE).equals(ATHORIZED_TRANSACTION);
      }

      return false;
    }

}
