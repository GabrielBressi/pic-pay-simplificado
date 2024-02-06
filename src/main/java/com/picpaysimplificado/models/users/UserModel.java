package com.picpaysimplificado.models.users;


import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.models.users.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity(name = "users")
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String document;
    @Column(unique = true)
    @Email
    private String email;
    @Size(min = 8, max = 20)
    private String password;
    private BigDecimal balance;

    private UserType userType;

    public UserModel(UserDTO data) {
        this.firstName = data.fistname();
        this.lastName = data.lastname();
        this.document = data.document();
        this.balance = data.balance();
        this.userType = data.userType();
        this.email = data.email();
        this.password = data.password();
    }

    public UserModel() {}

    public Long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
