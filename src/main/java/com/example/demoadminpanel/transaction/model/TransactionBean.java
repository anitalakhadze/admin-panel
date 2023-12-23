package com.example.demoadminpanel.transaction.model;

import com.example.demoadminpanel.transaction.entity.Transaction;
import com.example.demoadminpanel.transaction.model.enums.PaymentMethod;
import com.example.demoadminpanel.transaction.model.enums.PaymentStatus;
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

    public static TransactionBean transformFromTransactionBeanWithUserDetails(TransactionBeanWithUserDetails bean) {
        return TransactionBean.builder()
                .amount(bean.getAmount())
                .commission(bean.getCommission())
                .id(bean.getId())
                .invoiceData(bean.getInvoiceData())
                .dateCreated((Date) bean.getDateCreated())
                .details(TransactionDetails.builder()
                        .userId(bean.getUserId())
                        .paymentSource(getPaymentMethod(bean.getPaymentSource()))
                        .status(getPaymentStatus(bean.getStatus()))
                        .eCommerceCompanyName(bean.getCompanyName()).build())
                .build();
    }

    public static String getPaymentMethod(Integer statusId) {
        switch (statusId) {
            case 1:
                return PaymentMethod.CARD.getValue();
            case 2:
                return PaymentMethod.SAVED_CARD.getValue();
            case 3:
                return PaymentMethod.WALLET.getValue();
            case 4:
                return PaymentMethod.PIGGY_BANK.getValue();
            default:
                return PaymentMethod.NO_METHOD_FOUND.getValue();
        }
    }

    public static String getPaymentStatus(Integer status) {
        switch (status) {
            case 1:
                return PaymentStatus.SUCCESS.getValue();
            case 4:
                return PaymentStatus.FAILED.getValue();
            default:
                return PaymentStatus.PENDING.getValue();
        }
    }

}
