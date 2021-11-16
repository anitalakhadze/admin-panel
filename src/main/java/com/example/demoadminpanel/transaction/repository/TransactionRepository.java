package com.example.demoadminpanel.transaction.repository;

import com.example.demoadminpanel.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
