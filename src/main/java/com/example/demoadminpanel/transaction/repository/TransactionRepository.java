package com.example.demoadminpanel.transaction.repository;

import com.example.demoadminpanel.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value =
            " select * from admin_panel.transaction t " +
                    "left join admin_panel.user u " +
                    "ON t.ip = u.ip_address " +
                    "where t.date_created >= :startDate " +
                    "and t.date_created <= :endDate " +
                    "AND u.id = :companyId "
            , nativeQuery = true)
    List<Transaction> getTransactionsByRange(Long companyId, Date startDate, Date endDate);

    @Query(value =
            " select * from admin_panel.transaction t " +
                    "left join admin_panel.user u " +
                    "ON t.ip = u.ip_address " +
                    "where t.date_created >= :startDate " +
                    "and t.date_created <= :endDate " +
                    "AND u.id in (:companyIds) "
            , nativeQuery = true)
    List<Transaction> getTransactionsByCompanyIdAndRange(List<Long> companyIds, Date startDate, Date endDate);

}
