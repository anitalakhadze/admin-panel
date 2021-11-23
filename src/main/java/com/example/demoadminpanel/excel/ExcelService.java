package com.example.demoadminpanel.excel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExcelService {
    void generateExcelOfUsers(HttpServletResponse response) throws IOException;
    void generateExcelOfTransactions(HttpServletResponse response);
}
