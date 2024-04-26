package com.c104.seolo.domain.checklist.dto.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class CheckListInfo {
    private Long id;
    private String context;
}
