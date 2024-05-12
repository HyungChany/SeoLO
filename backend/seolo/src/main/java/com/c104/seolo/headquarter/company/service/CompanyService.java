package com.c104.seolo.headquarter.company.service;

import com.c104.seolo.headquarter.company.entity.Company;

public interface CompanyService {
    Company findCompanyEntityByCompanyCode(String companyCode);
}
