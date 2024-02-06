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

    public TransactionModel() {}

    public TransactionModel(UserModel payer, UserModel payee, BigDecimal amount, LocalDateTime timestamp) {
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return Id;
    }

    public UserModel getPayer() {
        return payer;
    }

    public void setPayer(UserModel payer) {
        this.payer = payer;
    }

    public UserModel getPayee() {
        return payee;
    }

    public void setPayee(UserModel payee) {
        this.payee = payee;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
