package com.example.demoadminpanel.transaction.repository;

import com.example.demoadminpanel.transaction.entity.Transaction;
import com.example.demoadminpanel.transaction.model.TransactionBeanWithUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select new com.example.demoadminpanel.transaction.model.TransactionBeanWithUserDetails" +
            "(t.id, t.amount, t.commission, t.dateCreated, t.invoiceData, u.id, u.name, t.status, t.moneySource) " +
                    "from Transaction t left join User u ON t.ip = u.ipAddress " +
                    "where t.dateCreated >= :startDate and t.dateCreated <= :endDate AND u.id = :companyId ")
    List<TransactionBeanWithUserDetails> getTransactionsBetweenRange(Long companyId, Date startDate, Date endDate);

    @Query(value = "select new com.example.demoadminpanel.transaction.model.TransactionBeanWithUserDetails" +
                    "(t.id, t.amount, t.commission, t.dateCreated, t.invoiceData, u.id, u.name, t.status, t.moneySource) " +
                    "from Transaction t left join User u ON t.ip = u.ipAddress " +
                    "where t.dateCreated >= :startDate and t.dateCreated <= :endDate AND u.id in (:companyIds) ")
    List<TransactionBeanWithUserDetails> getTransactionsUsingCompanyIdAndRange(List<Long> companyIds, Date startDate, Date endDate);

}
