package com.picpaysimplificado.services;

import com.picpaysimplificado.models.users.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

import static com.picpaysimplificado.constants.AppConstants.*;

@Service
public class AuthorizationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${transaction.checker}")
    private String transactionChecker;


    public boolean authorizeTransaction(UserModel user, BigDecimal value) {
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(transactionChecker, Map.class);

        if(authorizationResponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) authorizationResponse.getBody().get(MESSAGE) != null ? (String) authorizationResponse.getBody().get(MESSAGE) : EMPTY;
            return authorizationResponse.getBody().get(MESSAGE).equals(ATHORIZED_TRANSACTION);
        }

        return false;
    }
}
