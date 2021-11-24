package com.example.demoadminpanel.transaction.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TransactionSearchInfoBean {
    private List<Long> companyIds;
    private Date startDate;
    private Date endDate;
}
