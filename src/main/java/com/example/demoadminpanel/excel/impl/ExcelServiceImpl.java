package com.example.demoadminpanel.excel.impl;

import com.example.demoadminpanel.excel.ExcelService;
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

    @Override
    public void generateExcelOfUsers(HttpServletResponse response) throws IOException {
        List<User> users = userRepository.findAll();

        StringBuilder sb = new StringBuilder();
        sb.append("დასახელება");
        sb.append(",");
        sb.append("IP მისამართი");
        sb.append(",");
        sb.append("საიტის URL");
        sb.append(",");
        sb.append("სტატუსი");
        sb.append(",");
        sb.append("დამატების თარიღი");
        sb.append(",");
        sb.append("\n");

        for (User user : users) {
            sb.append(user.getName());
            sb.append(",");
            sb.append(user.getIpAddress());
            sb.append(",");
            sb.append(user.getReturnUrl());
            sb.append(",");
            sb.append(user.getIsActive() ? "აქტიური" : "არააქტიური");
            sb.append(",");
            sb.append(user.getAddedAt() == null ? "" : user.getAddedAt().toString());
            sb.append(",");
            sb.append("\n");
        }

        response.setContentType("application/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + "eCommerce კომპანიები.csv");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(sb.toString().getBytes());
        outputStream.close();
    }

    @Override
    public void generateExcelOfTransactions(HttpServletResponse response) {

    }
}
