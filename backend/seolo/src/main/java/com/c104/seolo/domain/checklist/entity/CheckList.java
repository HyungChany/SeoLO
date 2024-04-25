package com.c104.seolo.domain.checklist.entity;

import com.c104.seolo.headquarter.company.entity.Company;
import com.c104.seolo.global.common.BaseEntity;
import jakarta.persistence.*;
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
public class CheckList extends BaseEntity {
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "check_list_id")
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "check_list_context", length = 255)
    private String checkListContext;
}
