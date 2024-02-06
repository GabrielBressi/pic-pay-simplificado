package com.picpaysimplificado.models.transactions;

import com.picpaysimplificado.models.users.UserModel;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions")
@Table(name = "transactions")
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private UserModel payer;

    @ManyToOne
    @JoinColumn(name = "payee_id")
    private UserModel payee;
    private BigDecimal amount;

    private LocalDateTime timestamp;


}
