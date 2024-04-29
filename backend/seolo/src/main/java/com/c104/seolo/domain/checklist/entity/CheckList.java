package com.c104.seolo.domain.checklist.entity;

import com.c104.seolo.headquarter.company.entity.Company;
import com.c104.seolo.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@Entity
@Table(name = "check_list")
public class CheckList extends BaseEntity {
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "check_list_id", nullable = false)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_code", nullable = false)
    private Company company;

    @Column(name = "check_list_context", length = 255, nullable = false)
    private String checkListContext;

    @Builder
    private CheckList(Long id, Company company, String checkListContext) {
        this.id = id;
        this.company = company;
        this.checkListContext = checkListContext;
    }

    public CheckList() {}
}
