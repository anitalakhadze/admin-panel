package com.example.demoadminpanel.company.service;

import com.example.demoadminpanel.company.model.CompanyBean;
import com.example.demoadminpanel.exception.customExceptions.ResourceNotFoundException;
import com.example.demoadminpanel.user.entity.User;
import com.example.demoadminpanel.user.model.enums.UserStatus;
import com.example.demoadminpanel.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CompanyService {
    private final UserRepository userRepository;

    public List<CompanyBean> getCompanies(List<UserStatus> statuses) {
        log.debug("Getting companies with statuses: {}", statuses);

        List<User> companiesList = userRepository.findUsersByStatusIn(statuses);
        return companiesList
                .stream()
                .map(CompanyBean::transformFromUser)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deactivateByCompanyId(Long companyId) {
        log.debug("Deactivating company with id: {}", companyId);

        User user = userRepository
                .findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("id-Company with ID %s not found.", companyId)));

        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }

    @Transactional
    public void saveCompanies(List<Long> companyIds) {
        if (CollectionUtils.isEmpty(companyIds)) {
            log.error("Company ID list was null or empty");
            throw new RuntimeException("Company ID list was null or empty");
        }

        for (Long companyId: companyIds) {
            User user = userRepository
                    .findById(companyId)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("id-Company with ID %s not found.", companyId)));

            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);
        }
    }
}
