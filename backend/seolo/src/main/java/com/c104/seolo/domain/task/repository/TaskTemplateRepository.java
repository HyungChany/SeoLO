package com.c104.seolo.domain.task.repository;

import com.c104.seolo.domain.task.dto.TaskTemplateDto;
import com.c104.seolo.domain.task.entity.TaskTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, Long> {
    @Query("SELECT new com.c104.seolo.domain.task.dto.TaskTemplateDto( " +
            "t.id, t.taskType, t.precaution) FROM TaskTemplate t")
    List<TaskTemplateDto> findAllDto();
}
