package com.c104.seolo.headquarter.company.repository;

import com.c104.seolo.headquarter.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByCompanyCodeEquals(String companyCode);
}
