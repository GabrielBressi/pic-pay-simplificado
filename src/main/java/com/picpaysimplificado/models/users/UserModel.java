package com.picpaysimplificado.models.users;


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



}
