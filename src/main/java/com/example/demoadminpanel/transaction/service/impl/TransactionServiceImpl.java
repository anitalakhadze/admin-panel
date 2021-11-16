package com.example.demoadminpanel.transaction.service.impl;

import com.example.demoadminpanel.transaction.entity.Transaction;
import com.example.demoadminpanel.transaction.model.TransactionBean;
import com.example.demoadminpanel.transaction.repository.TransactionRepository;
import com.example.demoadminpanel.transaction.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public List<TransactionBean> getTransactions() {
        return transactionRepository
                .findAll()
                .stream()
                .map(TransactionBean::transformFromTransactionEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionBean getTransaction(Long id) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        // TODO check
        Transaction transaction = transactionOptional.get();
        return TransactionBean.transformFromTransactionEntity(transaction);
    }
}
