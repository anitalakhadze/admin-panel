package com.example.demoadminpanel.company.controller;

import com.example.demoadminpanel.company.model.CompanyBean;
import com.example.demoadminpanel.company.service.CompanyService;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping()
    public List<CompanyBean> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/active")
    public List<CompanyBean> getActiveCompanies() {
        return companyService.getActiveCompanies();
    }

    @GetMapping("/inactive")
    public List<CompanyBean> getInactiveCompanies() {
        return companyService.getInactiveCompanies();
    }

    @DeleteMapping("/{id}")
    public void deactivateByCompanyId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        companyService.deactivateByCompanyId(id);
    }

    @PostMapping
    public void saveAll(@RequestBody List<Long> companyIds) throws ResourceNotFoundException {
        companyService.saveAll(companyIds);
    }

}
