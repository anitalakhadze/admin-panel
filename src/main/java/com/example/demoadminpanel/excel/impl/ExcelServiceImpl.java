package com.example.demoadminpanel.excel.impl;

import com.example.demoadminpanel.excel.ExcelService;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.transaction.model.TransactionBean;
import com.example.demoadminpanel.transaction.model.TransactionBeanWithUserDetails;
import com.example.demoadminpanel.transaction.model.TransactionSearchInfoBean;
import com.example.demoadminpanel.transaction.service.TransactionService;
import com.example.demoadminpanel.user.entity.User;
import com.example.demoadminpanel.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class ExcelServiceImpl implements ExcelService {
    private final UserRepository userRepository;
    private final TransactionService transactionService;

    @Override
    public void generateExcelOfUsers(HttpServletResponse response) {
        List<User> users = userRepository.findAll();
        List<String> headerRow = getCompaniesHeaderRow();
        List<List<String>> companyRows = getCompaniesDataRows(users);
        generateExcel("კომპანიები", "კომპანიები.xlsx", headerRow, companyRows, response);
    }

    @Override
    public void generateExcelOfTransactions(TransactionSearchInfoBean bean, HttpServletResponse response) throws ResourceNotFoundException {
        List<TransactionBeanWithUserDetails> filteredTransactions = transactionService.getFilteredTransactionsWithDetails(bean);
        List<String> headerRow = getTransactionsHeaderRow();
        List<List<String>> transactionRows = getTransactionsDataRows(filteredTransactions);
        generateExcel("ტრანზაქციების ისტორია", "ტრანზაქციები.xlsx", headerRow, transactionRows, response);
    }

    private List<String> getCompaniesHeaderRow() {
        return new ArrayList<>(Arrays.asList(
                "ID",
                "დასახელება",
                "IP მისამართი",
                "საიტის URL",
                "სტატუსი",
                "დამატების თარიღი"));
    }

    private List<String> getTransactionsHeaderRow() {
        return new ArrayList<>(Arrays.asList(
                "ტრანზაქციის ID",
                "თანხა",
                "საკომისიო",
                "ინვოისის მონაცემები",
                "თარიღი",
                "გადახდის მეთოდი",
                "სტატუსი",
                "კომპანიის დასახელება"));
    }

    private List<List<String>> getCompaniesDataRows(List<User> companies) {
        List<List<String>> companyRows = new ArrayList<>();
        for (User company : companies) {
            companyRows.add(getCompaniesRowInfo(company));
        }
        return companyRows;
    }

    private List<List<String>> getTransactionsDataRows(List<TransactionBeanWithUserDetails> filteredTransactions) {
        List<List<String>> transactionRows = new ArrayList<>();
        for (TransactionBeanWithUserDetails bean : filteredTransactions) {
            transactionRows.add(getTransactionsRowInfo(bean));
        }
        return transactionRows;
    }

    private List<String> getCompaniesRowInfo(User company) {
        return new ArrayList<>(Arrays.asList(company.getId().toString(),
                company.getName(),
                company.getIpAddress(),
                company.getReturnUrl(),
                company.getIsActive() ? "აქტიური" : "არააქტიური",
                company.getAddedAt() == null ? "" : company.getAddedAt().toString()));
    }

    private List<String> getTransactionsRowInfo(TransactionBeanWithUserDetails bean) {
        return new ArrayList<>(Arrays.asList(String.valueOf(bean.getId()),
                String.valueOf(bean.getAmount()),
                String.valueOf(bean.getCommission()),
                bean.getInvoiceData(),
                bean.getDateCreated().toString(),
                TransactionBean.getPaymentMethod(bean.getPaymentSource()),
                TransactionBean.getPaymentStatus(bean.getStatus()),
                bean.getCompanyName()));
    }

    private void generateExcel(String sheetName, String filename,
                               List<String> headerRow, List<List<String>> transactionRows,
                               HttpServletResponse response) {
        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        SXSSFSheet spreadsheet = workbook.createSheet(sheetName);
        SXSSFRow row;

        Map<String, Object[]> data = new TreeMap<>();
        data.put("1", new Object[] { headerRow });

        for (int i = 0; i < transactionRows.size(); i++) {
            List<String> transactionRow = transactionRows.get(i);
            data.put(String.valueOf(i + 2), new Object[] { transactionRow });
        }

        Set<String> keySet = data.keySet();
        int rowNum = 0;
        for (String key : keySet) {
            row = spreadsheet.createRow(rowNum++);
            Object[] objectArr = data.get(key);
            int cellNum = 0;
            List<String> stringList = (List<String>) objectArr[0];
            for (String s : stringList) {
                Cell cell = row.createCell(cellNum++);
                cell.setCellValue(s);
            }
        }

        setContentTypeAndHeadersToResponse(response, filename);

        try {
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setContentTypeAndHeadersToResponse(HttpServletResponse response, String filename) {
        response.setContentType("application/xlsx");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
    }
}
