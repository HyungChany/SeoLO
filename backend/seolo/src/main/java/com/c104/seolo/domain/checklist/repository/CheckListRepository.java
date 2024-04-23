package com.c104.seolo.domain.checklist.repository;

import com.c104.seolo.domain.checklist.dto.info.CheckListInfo;
import com.c104.seolo.domain.checklist.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long> {
    @Query("SELECT c.id, c.checkListContext " +
            "FROM CheckList c " +
            "WHERE c.company.id = :company_id")
    Optional<List<CheckListInfo>> findByCompany(Long company_id);
}
