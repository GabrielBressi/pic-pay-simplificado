package com.picpaysimplificado.services;

import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.exceptions.UserShopKeeperTransactionException;
import com.picpaysimplificado.exceptions.UserWithNoBalanceException;
import com.picpaysimplificado.models.users.UserModel;
import com.picpaysimplificado.models.users.UserRole;
import com.picpaysimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(UserModel payer, BigDecimal amount) {
        if(payer.getRole() == UserRole.SHOPKEEPER) {
            throw new UserShopKeeperTransactionException();
        }

        if(payer.getBalance().compareTo(amount) < 0) {
            throw new UserWithNoBalanceException();
        }
    }

    public UserModel findUserById(Long userId) throws Exception {
        return this.userRepository.findUserByUserId(userId).orElseThrow(() -> new Exception("Usuário não encontrado."));
    }

    public List<UserModel> getAllUsers() {
        return this.userRepository.findAll();
    }

}
