package com.example.demoadminpanel.transaction.controller;

import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.transaction.model.TransactionBean;
import com.example.demoadminpanel.transaction.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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

    @PostMapping("/{transactionId}")
    public void cancelTransaction(@PathVariable Long transactionId, @RequestBody String username) throws ResourceNotFoundException {
        transactionService.cancelTransaction(transactionId, username);
    }
}
