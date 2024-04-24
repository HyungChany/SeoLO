package com.c104.seolo.domain.checklist.dto.response;

import com.c104.seolo.domain.checklist.dto.CheckListDto;
import com.c104.seolo.domain.checklist.dto.CheckListTemplateDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class GetCheckListResponse {
    private List<CheckListTemplateDto> basic_checklists;
    private List<CheckListDto> checklists;
}
