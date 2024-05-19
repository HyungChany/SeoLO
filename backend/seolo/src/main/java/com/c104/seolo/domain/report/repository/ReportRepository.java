package com.c104.seolo.domain.report.repository;

import com.c104.seolo.domain.report.entity.Report;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT COUNT(r) FROM Report r WHERE r.isAccident = true AND r.createdAt >= :startOfMonth AND r.createdAt < :endOfMonth")
    Long countByIsAccidentTrueAndCreatedAtBetween(@Param("startOfMonth") LocalDateTime startOfMonth, @Param("endOfMonth") LocalDateTime endOfMonth);

    List<Report> findAllByTaskStartDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
