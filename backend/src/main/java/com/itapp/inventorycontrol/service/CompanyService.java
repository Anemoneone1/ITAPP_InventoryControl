package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.entity.Company;
import com.itapp.inventorycontrol.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Company create(Company company) {
        return companyRepository.save(company);
    }
}
