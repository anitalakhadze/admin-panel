package com.example.demoadminpanel.transaction.service;

import com.example.demoadminpanel.transaction.model.TransactionBean;

import java.util.List;

public interface TransactionService {

    List<TransactionBean> getTransactions();
    TransactionBean getTransaction(Long id);
}
