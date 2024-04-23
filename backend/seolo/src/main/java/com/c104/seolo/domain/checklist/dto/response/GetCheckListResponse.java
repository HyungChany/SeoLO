package com.c104.seolo.domain.checklist.dto.response;

import com.c104.seolo.domain.checklist.dto.CheckListDto;
import com.c104.seolo.domain.checklist.dto.CheckListTemplateDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class GetCheckListResponse {
    private List<CheckListTemplateDto> basic_checklists;
    private Optional<List<CheckListDto>> checklists;

    public void setCheckLists(List<CheckListDto> checkListDtos) {
        this.checklists = Optional.of(checkListDtos);
    }
}
