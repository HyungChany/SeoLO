package com.c104.seolo.domain.checklist.dto.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CheckListTemplateInfo {
    private Long id;
    private String context;
}
