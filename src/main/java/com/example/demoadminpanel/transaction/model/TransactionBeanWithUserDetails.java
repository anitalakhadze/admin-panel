package com.example.demoadminpanel.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionBeanWithUserDetails {
    private long id;
    private long amount;
    private long commission;
    private java.util.Date dateCreated;
    private java.lang.String invoiceData;
    private long userId;
    private java.lang.String companyName;
    private int status;
    private int paymentSource;
}
