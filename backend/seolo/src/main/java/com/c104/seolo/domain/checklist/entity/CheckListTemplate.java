package com.c104.seolo.domain.checklist.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@Entity
@Table(name = "check_list_template")
public class CheckListTemplate {
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "check_list_template_id")
    @Id
    private Long id;

    @Column(name = "check_list_template_context", length = 255)
    private String context;

    @Builder
    private CheckListTemplate(Long id, String checkListTemplateContext) {
        this.id = id;
        this.context = checkListTemplateContext;
    }

    public CheckListTemplate() {}
}
