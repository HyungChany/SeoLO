package com.c104.seolo.domain.checklist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class CheckListTemplate {
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "check_list_template_id")
    @Id
    private Long id;

    @Column(name = "check_list_template_context", length = 255)
    private String checkListTemplateContext;
}
