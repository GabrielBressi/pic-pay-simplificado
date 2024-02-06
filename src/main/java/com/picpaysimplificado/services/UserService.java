package com.picpaysimplificado.services;

import com.picpaysimplificado.models.users.UserModel;
import com.picpaysimplificado.models.users.UserType;
import com.picpaysimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(UserModel payer, BigDecimal amount) throws Exception {
        if(payer.getUserType() == UserType.SHOPKEEPER) {
            throw new Exception("Usuário do tipo logista não está autorizado a realizar transação.");
        }

        if(payer.getBalance().compareTo(amount) < 0) {
            throw new Exception(String.format("Usuário id:%s não possui saldo suficiente", payer.getUserId().toString()));
        }
    }

    public UserModel findUserById(Long userId) throws Exception {
        return this.userRepository.findUserByUserId(userId).orElseThrow(() -> new Exception("Usuário não encontrado."));
    }

    public void saveUser(UserModel user) {
        this.userRepository.save(user);
    }
}
