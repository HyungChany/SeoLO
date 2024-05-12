package com.c104.seolo.domain.core.entity;

import com.c104.seolo.global.common.BaseEntity;
import com.c104.seolo.headquarter.company.entity.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
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

    @Column(name = "locker_uid", length = 32, nullable = false, unique = true)
    private String uid;

    @Column(name = "locker_isLocked")
    private boolean isLocked;

    @Column(name = "locker_battery", nullable = false, columnDefinition = "INTEGER DEFAULT 100")
    private Integer battery;

    @Column(name = "locker_encryption_key", length = 32,nullable = false)
    private String encryptionKey;

    public void changeBattery(Integer newBattery) {
        this.battery = newBattery;
    }

    protected Locker () {}

    public Locker(Builder builder) {
        this.company = builder.company;
        this.uid = builder.uid;
        this.isLocked = builder.isLocked;
        this.battery = builder.battery;
        this.encryptionKey = builder.encryptionKey;
    }

    public static class Builder {
        private Company company;
        private String uid;
        private boolean isLocked = false;
        private int battery;
        private String encryptionKey;

        public Builder company(Company company) {
            if (company == null) {
                throw new IllegalArgumentException("Company cannot be null");
            }
            this.company = company;
            return this;
        }

        public Builder uid(String uid) {
            if (uid == null) {
                throw new IllegalArgumentException("Uid cannot be null");
            }
            this.uid = uid;
            return this;
        }

        public Builder isLocked(boolean isLocked) {
            this.isLocked = isLocked;
            return this;
        }

        public Builder battery(int battery) {
            this.battery = battery;
            return this;
        }

        public Builder encryptionKey(String newEncryptionKey) {
            if (newEncryptionKey == null) {
                throw new IllegalArgumentException("EncryptionKey cannot be null");
            }
            this.encryptionKey = newEncryptionKey;
            return this;
        }

        public Locker build() {
            if (company == null || uid == null || encryptionKey == null ) {
                throw new IllegalStateException("Cannot build Locker object, one or more required fields are not set");
            }
            return new Locker(this);
        }
    }
    public static Builder builder() {
        return new Builder();
    }
}
