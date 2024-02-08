package com.picpaysimplificado.repositories;

import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.models.users.UserModel;
import com.picpaysimplificado.models.users.UserRole;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get User successfully from DB")
    void findUserByDocumentSuccess() {
        String document = "99999999901";
        UserDTO data = new UserDTO(
                "Joao",
                "Teste",
                document,
                new BigDecimal(10),
                "test@gmail.com",
                "12345678",
                UserRole.COMMON
        );
        this.createUser(data);

        Optional<UserModel> result = this.userRepository.findUserByDocument(document);

        assertThat(result.isPresent()).isTrue();

    }

    @Test
    @DisplayName("Should not get User successfully from DB when user doesn't exist")
    void findUserByDocumentCase2() {
        String document = "99999999901";

        Optional<UserModel> result = this.userRepository.findUserByDocument(document);

        assertThat(result.isEmpty()).isTrue();

    }

    private UserModel createUser(UserDTO data) {
        UserModel newUser = new UserModel(data);
        this.entityManager.persist(newUser);
        return newUser;
    }
}