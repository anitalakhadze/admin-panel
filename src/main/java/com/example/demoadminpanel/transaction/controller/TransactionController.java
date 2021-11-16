package com.example.demoadminpanel.transaction.controller;

import com.example.demoadminpanel.transaction.model.TransactionBean;
import com.example.demoadminpanel.transaction.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping()
    public List<TransactionBean> getTransactions() {
        return transactionService.getTransactions();
    }

    @GetMapping("/{id}")
    public TransactionBean getTransaction(@PathVariable Long id) {
        return transactionService.getTransaction(id);
    }
}
