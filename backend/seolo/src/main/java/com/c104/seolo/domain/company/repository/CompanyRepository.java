package com.c104.seolo.domain.company.repository;

import com.c104.seolo.domain.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByCompanyCode(String companyCode);
}
