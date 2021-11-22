package com.example.demoadminpanel.company.service;

import com.example.demoadminpanel.company.model.CompanyBean;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;

import java.util.List;

public interface CompanyService {
    List<CompanyBean> getActiveCompanies();
    List<CompanyBean> getInactiveCompanies();
    List<CompanyBean> getAllCompanies();
    void deactivateByCompanyId(Long companyId) throws ResourceNotFoundException;
    void saveAll(List<Long> companyIds) throws ResourceNotFoundException;
}
