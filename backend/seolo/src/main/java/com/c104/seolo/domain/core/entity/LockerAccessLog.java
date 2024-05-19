package com.c104.seolo.domain.core.entity;

import com.c104.seolo.domain.core.enums.CODE;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.ToString;

@ToString
@Entity
@Table(name="locker_access_log")
public class LockerAccessLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locker_id", referencedColumnName = "locker_id", nullable = false)
    private Locker locker;

    @Column(name = "locker_access_log_code", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private CODE statusCode;

    protected LockerAccessLog() {}

    public LockerAccessLog(Builder builder) {
        this.appUser = builder.appUser;
        this.locker = builder.locker;
        this.statusCode = builder.statusCode;
    }

    public static class Builder {
        private AppUser appUser;
        private Locker locker;
        private CODE statusCode;

        public Builder appUser(AppUser newAppUser) {
            if (newAppUser == null) {
                throw new IllegalArgumentException("AppUser cannot be null");
            }
            this.appUser = newAppUser;
            return this;
        }

        public Builder locker(Locker newLocker) {
            if (newLocker == null) {
                throw new IllegalArgumentException("Locker cannot be null");
            }
            this.locker = newLocker;
            return this;
        }

        public Builder statusCode(CODE newCode) {
            if (newCode == null) {
                throw new IllegalArgumentException("statusCode cannot be null");
            }
            this.statusCode = newCode;
            return this;
        }

        public LockerAccessLog build() {
            if (appUser == null || locker == null || statusCode == null) {
                throw new IllegalStateException("Cannot build LockerAccessLog object, one or more required fields are not set");
            }
            return new LockerAccessLog(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
