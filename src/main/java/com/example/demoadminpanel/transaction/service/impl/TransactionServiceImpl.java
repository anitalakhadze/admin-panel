package com.example.demoadminpanel.transaction.service.impl;

import com.example.demoadminpanel.email.EmailSender;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.transaction.entity.Transaction;
import com.example.demoadminpanel.transaction.model.TransactionBean;
import com.example.demoadminpanel.transaction.model.TransactionSearchInfoBean;
import com.example.demoadminpanel.transaction.repository.TransactionRepository;
import com.example.demoadminpanel.transaction.service.TransactionService;
import com.example.demoadminpanel.user.entity.User;
import com.example.demoadminpanel.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final EmailSender emailSender;

    @Override
    public List<TransactionBean> getTransactions() {
        return transactionRepository
                .findAll()
                .stream()
                .map(TransactionBean::transformFromTransactionEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionBean> getFilteredTransactions(TransactionSearchInfoBean transactionSearchInfoBean) throws ResourceNotFoundException {
        Date startDate = transactionSearchInfoBean.getStartDate();
        Date endDate = transactionSearchInfoBean.getEndDate();
        List<Long> companyIds = transactionSearchInfoBean.getCompanyIds();
        if (!companyIds.isEmpty()) {
            return transactionRepository
                    .getTransactionsByCompanyIdAndRange(companyIds, startDate, endDate)
                    .stream()
                    .map(TransactionBean::transformFromTransactionEntity)
                    .collect(Collectors.toList());
        } else {
            String username = transactionSearchInfoBean.getUsername();
            User user = userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("User with username: %s not found", username)));
            return transactionRepository
                    .getTransactionsByRange(user.getId(), startDate, endDate)
                    .stream()
                    .map(TransactionBean::transformFromTransactionEntity)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public TransactionBean getTransaction(Long id) throws ResourceNotFoundException {
        Transaction transaction = transactionRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Transaction with ID %s was not found", id)));
        return TransactionBean.transformFromTransactionEntity(transaction);
    }

    @Override
    public void cancelTransaction(Long transactionId, String username) throws ResourceNotFoundException {
        Transaction transaction = transactionRepository
                .findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Transaction with id %s was not found", transactionId)));
        emailSender.send("atala15@freeuni.edu.ge", "ტრანზაქციის გაუქმება", getTransactionCancellationText(transaction, username));
    }

    private String getTransactionCancellationText(Transaction transaction, String username) {
        return String.format("<html><body><h1>მომხმარებელმა %s გამოგიგზავნათ ტრანზაქციის გაუქმების მოთხოვნა.", username) + "</h1>\n" +
                "<h2>ტრანზაქციის დეტალები: " + "</h2>\n" +
                "<ul><li>ტრანზაქციის ID: " + transaction.getId() + "</li>\n" +
                "<li>თანხა: " + transaction.getAmount() + "</li>\n" +
                "<li>Basket ID: " + transaction.getBasketId() + "</li>\n" +
                "<li>ტრანზაქციის შექმნის თარიღი: " + transaction.getDateCreated() + "</li>\n" +
                "<li>ინვოისის მონაცემები: " + transaction.getInvoiceData() + "</li>\n" +
                "<li>ტერმინალის ID: " + transaction.getTerminalId() + "</li>\n" +
                "<li>ტრანზაქციის ID: " + transaction.getTransactionId() + "</li></ul><body></html>\n";
    }
}
