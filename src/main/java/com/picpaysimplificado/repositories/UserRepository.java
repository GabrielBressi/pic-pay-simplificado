package com.picpaysimplificado.repositories;

import com.picpaysimplificado.models.users.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findUserByDocument(String document);
    Optional<UserModel> findUserByUserId(Long userId);
}
