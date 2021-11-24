package com.example.demoadminpanel.transaction.service;

import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.transaction.model.TransactionBean;
import com.example.demoadminpanel.transaction.model.TransactionSearchInfoBean;

import java.util.List;

public interface TransactionService {

    List<TransactionBean> getTransactions();
    List<TransactionBean> getFilteredTransactions(TransactionSearchInfoBean transactionSearchInfoBean);
    TransactionBean getTransaction(Long id) throws ResourceNotFoundException;
    void cancelTransaction(Long transactionId, String username) throws ResourceNotFoundException;

}
