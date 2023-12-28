package com.example.demoadminpanel.company.controller;

import com.example.demoadminpanel.company.model.CompanyBean;
import com.example.demoadminpanel.company.service.CompanyService;
import com.example.demoadminpanel.user.model.enums.UserStatus;
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

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyBean> getCompanies(@RequestParam("statuses") List<UserStatus> statuses) {
        return companyService.getCompanies(statuses);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deactivateByCompanyId(@PathVariable("id") Long id) {
        companyService.deactivateByCompanyId(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void saveCompanies(@RequestBody List<Long> companyIds) {
        companyService.saveCompanies(companyIds);
    }

}
