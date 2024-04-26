package com.c104.seolo.domain.checklist.repository;

import com.c104.seolo.domain.checklist.dto.info.CheckListInfo;
import com.c104.seolo.domain.checklist.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long> {
    @Query("select new com.c104.seolo.domain.checklist.dto.info.CheckListInfo( " +
            "c.id, c.checkListContext ) " +
            "FROM CheckList c " +
            "WHERE c.company.companyCode = :company_code")
    Optional<List<CheckListInfo>> findByCompanyEquals(String company_code);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CheckList c " +
            "WHERE LOWER(c.checkListContext) = LOWER(:checkListContext) and c.company.companyCode = :company_code")
    boolean existsByCheckListContextIgnoreCase(@Param("checkListContext") String checkListContext, @Param("company_code") String company_code);
}
