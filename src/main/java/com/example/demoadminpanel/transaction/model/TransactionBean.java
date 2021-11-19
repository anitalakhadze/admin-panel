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
                        .paymentSource(getPaymentMethod(transactionEntity.getMoneySource()))
                        .status(getPaymentStatus(transactionEntity.getStatus())).build())
                .build();
    }

    private static String getPaymentMethod(Integer statusId) {
        switch (statusId) {
            case 1:
                return "ბარათი";
            case 2:
                return "შენახული ბარათი";
            case 3:
                return "საფულე";
            case 4:
                return "ყულაბა";
            default:
                return "";
        }
    }

    private static String getPaymentStatus(Integer status) {
        switch (status) {
            case 1:
                return "წარმატებული";
            case 4:
                return "წარუმატებელი";
            default:
                return "პროცესშია";
        }
    }

}
