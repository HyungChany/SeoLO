package com.c104.seolo.domain.checklist.repository;

import com.c104.seolo.domain.checklist.dto.info.CheckListTemplateInfo;
import com.c104.seolo.domain.checklist.entity.CheckListTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckListTemplateRepository extends JpaRepository<CheckListTemplate, Long> {
    @Query("select new com.c104.seolo.domain.checklist.dto.info.CheckListTemplateInfo( " +
            "c.id, c.context ) " +
            "from CheckListTemplate c ")
    List<CheckListTemplateInfo> getCheckListTemplates();

}
