package com.example.demoadminpanel.excel;

import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.transaction.model.TransactionSearchInfoBean;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExcelService {
    void generateExcelOfUsers(HttpServletResponse response) throws IOException;
    void generateExcelOfTransactions(TransactionSearchInfoBean transactionSearchInfoBean, HttpServletResponse response) throws IOException, ResourceNotFoundException;
}
