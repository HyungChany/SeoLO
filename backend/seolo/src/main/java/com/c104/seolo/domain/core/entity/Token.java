package com.c104.seolo.domain.core.entity;

import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Entity
@Table(name = "token")
public class Token extends BaseEntity {

    @Id
    @Column(name = "token_value", nullable = false)
    private String tokenValue;

    @ManyToOne
    @JoinColumn(name = "locker_id", referencedColumnName = "locker_id", nullable = false)
    private Locker locker;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private AppUser appUser;

    protected Token() {}

    public Token(Builder builder) {
        this.tokenValue = tokenValue;
        this.locker = locker;
        this.appUser = appUser;
    }

    public static class Builder {
        private String tokenValue;
        private Locker locker;
        private AppUser appUser;

        public Builder tokenValue(String newTokenValue) {
            if (newTokenValue == null) {
                throw new IllegalArgumentException("tokenValue cannot be null");
            }
            this.tokenValue = newTokenValue;
            return this;
        }

        public Builder locker(Locker newLocker) {
            if (newLocker == null) {
                throw new IllegalArgumentException("locker cannot be null");
            }
            this.locker = newLocker;
            return this;
        }

        public Builder appUser(AppUser newAppUser) {
            this.appUser = newAppUser;
            return this;
        }

        public Token build() {
            if (tokenValue == null || locker == null || appUser == null) {
                throw new IllegalArgumentException("Cannot build Token object, one or more required fields are not set");
            }
            return new Token(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
