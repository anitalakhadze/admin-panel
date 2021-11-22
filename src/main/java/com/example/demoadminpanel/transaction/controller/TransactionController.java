package com.example.demoadminpanel.transaction.controller;

import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.transaction.model.TransactionBean;
import com.example.demoadminpanel.transaction.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionBean> getTransactions() {
        return transactionService.getTransactions();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ResponseStatus(HttpStatus.OK)
    public TransactionBean getTransaction(@PathVariable Long id) throws ResourceNotFoundException {
        return transactionService.getTransaction(id);
    }

    @PostMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void cancelTransaction(@PathVariable Long transactionId, @RequestBody String username) throws ResourceNotFoundException {
        transactionService.cancelTransaction(transactionId, username);
    }
}
