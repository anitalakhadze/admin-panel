package com.example.demoadminpanel.company.controller;

import com.example.demoadminpanel.company.model.CompanyBean;
import com.example.demoadminpanel.company.service.CompanyService;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyBean> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyBean> getActiveCompanies() {
        return companyService.getActiveCompanies();
    }

    @GetMapping("/inactive")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyBean> getInactiveCompanies() {
        return companyService.getInactiveCompanies();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deactivateByCompanyId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        companyService.deactivateByCompanyId(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void saveAll(@RequestBody List<Long> companyIds) throws ResourceNotFoundException {
        companyService.saveAll(companyIds);
    }

}
