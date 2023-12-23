package com.example.demoadminpanel.company.service.impl;

import com.example.demoadminpanel.company.model.CompanyBean;
import com.example.demoadminpanel.company.service.CompanyService;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.user.entity.User;
import com.example.demoadminpanel.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final UserRepository userRepository;

    @Override
    public List<CompanyBean> getActiveCompanies() {
        List<User> activeCompaniesList = userRepository.findUsersByIsActive(true);
        return activeCompaniesList
                .stream()
                .map(CompanyBean::transformFromUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompanyBean> getInactiveCompanies() {
        List<User> inactiveCompaniesList = userRepository.findUsersByIsActive(false);
        return inactiveCompaniesList
                .stream()
                .map(CompanyBean::transformFromUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompanyBean> getAllCompanies() {
        List<User> companiesList = userRepository.findAll();
        return companiesList
                .stream()
                .map(CompanyBean::transformFromUser)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivateByCompanyId(Long companyId) throws ResourceNotFoundException {
        User user = userRepository
                .findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Such company was not found"));
        user.setIsActive(false);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void saveAll(List<Long> companyIds) throws ResourceNotFoundException {
        if (companyIds != null && !companyIds.isEmpty()) {
            for (Long companyId: companyIds) {
                User user = userRepository
                        .findById(companyId)
                        .orElseThrow(() -> new ResourceNotFoundException("Such company was not found"));
                user.setIsActive(true);
                userRepository.save(user);
            }
        } else {
            throw new RuntimeException("Company ID list was null or empty");
        }
    }
}
