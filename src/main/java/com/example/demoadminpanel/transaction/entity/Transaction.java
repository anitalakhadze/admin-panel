package com.example.demoadminpanel.transaction.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "BASKET_ID")
    private String basketId;

    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    @Column(name = "PROCESSING_PRODUCT_ID")
    private Long processingProductId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "IP")
    private String ip;

    @Column(name = "SOURCETYPE")
    private Integer sourceType;

    @Column(name = "MONEYSOURCE")
    private Integer moneySource;

    @Column(name = "CARDMASK")
    private String cardMask;

    @Column(name = "COMMISSION")
    private Long commission;

    @Column(name = "AMOUNT")
    private Long amount;

    @Column(name = "TOTAL_AMOUNT")
    private Long totalAmount;

    @Column(name = "INVOICE_DATA")
    private String invoiceData;

    @Column(name = "CARDU_CODE")
    private String carduCode;

    @Column(name = "MERCHANTRECORD_ID")
    private Long merchantRecordId;

    @Column(name = "MERCHANT_TYPE")
    private Long merchantType;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "DATE_CREATED")
    private Date dateCreated;

    @Column(name = "CARD_TRN_ID")
    private Long cardTrnId;

    @Column(name = "BASKET_ID_FOR_PROCESSING")
    private String basketIdForProcessing;

    @Column(name = "UNIQ_ID")
    private Long uniqId;

    @Column(name = "AUTHENTICATION_MODE")
    private Integer authenticationMode;

    @Column(name = "TERMINAL_ID")
    private Long terminalId;

    @Column(name = "PAYMENT_TYPE")
    private Integer paymentType;

    @Column(name = "VERIFY_BASKET_ID")
    private String verifyBasketId;

    @Column(name = "FAV_ID")
    private Long favId;
}
