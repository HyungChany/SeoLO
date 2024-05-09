package com.c104.seolo.domain.task.dto.response;

import com.c104.seolo.domain.task.dto.RenameTaskTemplate;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class RenameTaskTemplateResponse {
    private List<RenameTaskTemplate> templates;


    @Builder
    public RenameTaskTemplateResponse(List<RenameTaskTemplate> templates) {
        this.templates = templates;
    }
}
