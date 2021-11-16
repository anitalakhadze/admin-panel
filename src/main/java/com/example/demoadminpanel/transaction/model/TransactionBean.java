package com.example.demoadminpanel.transaction.model;

import com.example.demoadminpanel.transaction.entity.Transaction;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionBean {

    private Long id;
    private Long amount;
    private Long commission;
    private Date dateCreated;
    private String invoiceData;
    private TransactionDetails details;

    public static TransactionBean transformFromTransactionEntity(Transaction transactionEntity) {
        return TransactionBean.builder()
                .amount(transactionEntity.getAmount())
                .commission(transactionEntity.getCommission())
                .id(transactionEntity.getId())
                .invoiceData(transactionEntity.getInvoiceData())
                .dateCreated(transactionEntity.getDateCreated())
                .details(TransactionDetails.builder()
                        .userId(transactionEntity.getUserId())
                        .status(transactionEntity.getStatus()).build())
                .build();
    }
}
