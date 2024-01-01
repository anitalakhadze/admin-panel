package com.example.demoadminpanel.transaction.controller;

import com.example.demoadminpanel.excel.ExcelService;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.transaction.model.TransactionBean;
import com.example.demoadminpanel.transaction.model.TransactionSearchInfoBean;
import com.example.demoadminpanel.transaction.service.TransactionService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final ExcelService excelService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionBean> getTransactions() {
        return transactionService.getTransactions();
    }

    @PostMapping("/filtered")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionBean> getFilteredTransactions(@RequestBody TransactionSearchInfoBean transactionSearchInfoBean) throws ResourceNotFoundException {
        return transactionService.getFilteredTransactions(transactionSearchInfoBean);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ResponseStatus(HttpStatus.OK)
    public TransactionBean getTransaction(@PathVariable Long id) throws ResourceNotFoundException {
        return transactionService.getTransaction(id);
    }

    @PostMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void cancelTransaction(@PathVariable Long transactionId, @RequestBody String username) throws ResourceNotFoundException {
        transactionService.cancelTransaction(transactionId, username);
    }

    @PostMapping(value = "/filtered/excel", produces = "application/csv")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ResponseStatus(HttpStatus.OK)
    public void generateExcelOfTransactions(@RequestBody TransactionSearchInfoBean bean, HttpServletResponse response) throws IOException, ResourceNotFoundException {
        excelService.generateExcelOfTransactions(bean, response);
    }
}
