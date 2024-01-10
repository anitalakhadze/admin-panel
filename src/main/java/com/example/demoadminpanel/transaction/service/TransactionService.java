package com.example.demoadminpanel.transaction.service;

import com.example.demoadminpanel.email.EmailService;
import com.example.demoadminpanel.exception.custom.ResourceNotFoundException;
import com.example.demoadminpanel.transaction.entity.Transaction;
import com.example.demoadminpanel.transaction.model.TransactionBean;
import com.example.demoadminpanel.transaction.model.TransactionBeanWithUserDetails;
import com.example.demoadminpanel.transaction.model.TransactionSearchInfoBean;
import com.example.demoadminpanel.transaction.repository.TransactionRepository;
import com.example.demoadminpanel.user.entity.User;
import com.example.demoadminpanel.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public List<TransactionBean> getTransactions() {
        return transactionRepository
                .findAll()
                .stream()
                .map(TransactionBean::transformFromTransactionEntity)
                .collect(Collectors.toList());
    }

    public List<TransactionBean> getFilteredTransactions(TransactionSearchInfoBean transactionSearchInfoBean) throws ResourceNotFoundException {
        return getFilteredTransactionsWithDetails(transactionSearchInfoBean)
                .stream()
                .map(TransactionBean::transformFromTransactionBeanWithUserDetails)
                .collect(Collectors.toList());
    }

    public List<TransactionBeanWithUserDetails> getFilteredTransactionsWithDetails(TransactionSearchInfoBean bean) throws ResourceNotFoundException {
        Date startDate = bean.getStartDate();
        Date endDate = bean.getEndDate();
        List<Long> companyIds = bean.getCompanyIds();
        if (!companyIds.isEmpty()) {
            return transactionRepository
                    .getTransactionsUsingCompanyIdAndRange(companyIds, startDate, endDate);
        } else {
            String username = bean.getUsername();
            User user = userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("User with username: %s not found", username)));
            return transactionRepository
                    .getTransactionsBetweenRange(user.getId(), startDate, endDate);
        }
    }

    public TransactionBean getTransaction(Long id) throws ResourceNotFoundException {
        Transaction transaction = transactionRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Transaction with ID %s was not found", id)));
        return TransactionBean.transformFromTransactionEntity(transaction);
    }

    public void cancelTransaction(Long transactionId, String username) throws ResourceNotFoundException {
        Transaction transaction = transactionRepository
                .findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Transaction with id %s was not found", transactionId)));
        emailService.send("a.talakhadze@oppa.ge", "Cancel Transaction", getTransactionCancellationText(transaction, username));
    }

    private String getTransactionCancellationText(Transaction transaction, String username) {
        return String.format("<html><body><h1>User %s has requested to cancel a transaction.", username) + "</h1>\n" +
                "<h2>Transaction Details: " + "</h2>\n" +
                "<ul><li>Transaction ID: " + transaction.getId() + "</li>\n" +
                "<li>Amount: " + transaction.getAmount() + "</li>\n" +
                "<li>Basket ID: " + transaction.getBasketId() + "</li>\n" +
                "<li>Transaction Initiation Date: " + transaction.getDateCreated() + "</li>\n" +
                "<li>Invoice Data: " + transaction.getInvoiceData() + "</li>\n" +
                "<li>Terminal ID: " + transaction.getTerminalId() + "</li>\n" +
                "<li>Internal Transaction ID: " + transaction.getTransactionId() + "</li></ul><body></html>\n";
    }
}
