package com.c104.seolo.headquarter.company.service.impl;

import com.c104.seolo.headquarter.company.entity.Company;
import com.c104.seolo.headquarter.company.repository.CompanyRepository;
import com.c104.seolo.headquarter.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;

    }

    @Override
    public Company findCompanyEntityByCompanyCode(String companyCode) {
        return companyRepository.findByCompanyCodeEquals(companyCode);
    }
}
