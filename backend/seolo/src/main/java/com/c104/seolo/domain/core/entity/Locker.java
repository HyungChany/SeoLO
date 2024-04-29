package com.c104.seolo.domain.core.entity;

import com.c104.seolo.global.common.BaseEntity;
import com.c104.seolo.headquarter.company.entity.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Entity
@Table(name = "locker")
public class Locker extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locker_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_code", referencedColumnName = "company_code", nullable = false)
    private Company company;

    @Column(name = "locker_isLocked")
    private boolean isLocked;

    protected Locker () {}

    public Locker(Builder builder) {
        this.company = builder.company;
        this.isLocked = builder.isLocked;
    }

    public static class Builder {
        private Company company;
        private boolean isLocked = false;

        public Builder company(Company company) {
            if (company == null) {
                throw new IllegalArgumentException("Company cannot be null");
            }
            this.company = company;
            return this;
        }

        public Builder isLocked(boolean isLocked) {
            this.isLocked = isLocked;
            return this;
        }

        public Locker build() {
            if (company == null) {
                throw new IllegalStateException("Cannot build Locker object, one or more required fields are not set");
            }
            return new Locker(this);
        }
    }
    public static Builder builder() {
        return new Builder();
    }
}
