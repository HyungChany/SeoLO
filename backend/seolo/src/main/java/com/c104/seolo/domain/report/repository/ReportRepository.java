package com.c104.seolo.domain.report.repository;

import com.c104.seolo.domain.report.entity.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long> {
}
