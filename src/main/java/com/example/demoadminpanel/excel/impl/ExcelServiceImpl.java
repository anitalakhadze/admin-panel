package com.example.demoadminpanel.excel.impl;

import com.example.demoadminpanel.excel.ExcelService;
import com.example.demoadminpanel.transaction.model.TransactionBean;
import com.example.demoadminpanel.transaction.model.TransactionDetails;
import com.example.demoadminpanel.transaction.model.TransactionSearchInfoBean;
import com.example.demoadminpanel.transaction.service.TransactionService;
import com.example.demoadminpanel.user.entity.User;
import com.example.demoadminpanel.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ExcelServiceImpl implements ExcelService {
    private final UserRepository userRepository;
    private final TransactionService transactionService;

    @Override
    public void generateExcelOfUsers(HttpServletResponse response) throws IOException {
        List<User> users = userRepository.findAll();

        String header = getFormattedStringForExcel(
                "დასახელება",
                "IP მისამართი",
                "საიტის URL",
                "სტატუსი",
                "დამატების თარიღი");
        StringBuilder sb = new StringBuilder();
        sb.append(header);

        for (User user : users) {
            sb.append(getFormattedStringForExcel(
                    user.getName(),
                    user.getIpAddress(),
                    user.getReturnUrl(),
                    user.getIsActive() ? "აქტიური" : "არააქტიური",
                    user.getAddedAt() == null ? "" : user.getAddedAt().toString()));
        }

        response.setContentType("application/csv;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + "eCommerce კომპანიები.csv");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(sb.toString().getBytes());
        outputStream.close();
    }

    @Override
    public void generateExcelOfTransactions(TransactionSearchInfoBean transactionSearchInfoBean, HttpServletResponse response) throws IOException {
        List<TransactionBean> filteredTransactions = transactionService.getFilteredTransactions(transactionSearchInfoBean);

        String header = getFormattedStringForExcel(
                "ტრანზაქციის ID",
                "თანხა",
                "საკომისიო",
                "ინვოისის მონაცემები",
                "თარიღი",
                "სტატუსი");
        StringBuilder sb = new StringBuilder();
        sb.append(header);

        for (TransactionBean transactionBean : filteredTransactions) {
            TransactionDetails details = transactionBean.getDetails();
            sb.append(getFormattedStringForExcel(
                    transactionBean.getId().toString(),
                    transactionBean.getAmount().toString(),
                    transactionBean.getCommission().toString(),
                    transactionBean.getInvoiceData(),
                    transactionBean.getDateCreated().toString(),
                    details.getStatus()));
        }

        response.setContentType("application/csv;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + "ტრანზაქციები.csv");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(sb.toString().getBytes());
        outputStream.close();
    }

    private String getFormattedStringForExcel(String... args) {
        return String.join(",", args) + ",\n";
    }
}
