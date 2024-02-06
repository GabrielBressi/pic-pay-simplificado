package com.picpaysimplificado.services;

import com.picpaysimplificado.dtos.NotificationDTO;
import com.picpaysimplificado.models.users.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${transaction.notification}")
    private String trasactionNotification;

    public void sendNotification(UserModel user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(user.getEmail(), message);

        ResponseEntity<String> notificationResponse = restTemplate.postForEntity(trasactionNotification, notificationRequest, String.class);

        if(!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
            throw new Exception("Serviço de notificação indisponivel");
        }
    }
}
