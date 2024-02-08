package com.picpaysimplificado.controllers;

import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.models.transactions.TransactionModel;
import com.picpaysimplificado.services.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionModel> createTransaction(@RequestBody TransactionDTO transaction, HttpServletRequest request) throws Exception {
        TransactionModel transactionModel = this.transactionService.createTransaction(transaction, request);

        return new ResponseEntity<>(transactionModel, HttpStatus.CREATED);
    }

}
