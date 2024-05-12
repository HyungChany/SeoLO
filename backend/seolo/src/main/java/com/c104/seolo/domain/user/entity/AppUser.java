package com.c104.seolo.domain.user.entity;

import com.c104.seolo.domain.core.enums.CODE;
import com.c104.seolo.domain.user.enums.ROLES;
import com.c104.seolo.global.common.BaseEntity;
import com.c104.seolo.headquarter.employee.entity.Employee;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;

@ToString
@Getter
@Entity
@Table(name = "app_user")
public class AppUser extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_num", referencedColumnName = "employee_num", nullable = false)
    private Employee employee;

    @Column(name = "user_role", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private ROLES ROLES;

    @Column(name = "user_stat", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private CODE statusCODE;

    @Column(name = "user_pwd", length = 72, nullable = false)
    private String password;

    @Column(name = "user_pin", length = 72, nullable = false)
    // 기본값으로 사용자의 '월일 ex 0223 으로 지정한다'
    private String PIN;

    @Column(name = "user_fail_count")
    // failCount 5회 이상시 잠김
    private Integer failCount;

    @Column(name = "user_isLocked")
    private boolean isLocked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.ROLES.name()));
    }

    @Override
    public String getUsername() {
        return this.employee.getEmployeeNum(); // 사번을 username 으로한다.
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changePin(String newPin) { this.PIN = newPin; }

    public String getUserTitle() {
        return this.employee.getEmployeeTitle();
    }

    public String getUserTeam() {
        return this.employee.getEmployeeTeam();
    }

    public ROLES getUserRole() {
        return this.ROLES;
    }

    public Integer upFailCount() {
        if (this.failCount == null) {
            this.failCount = 0;
        }

        this.failCount++;

        if (this.failCount >= 5) {
            this.isLocked = true;
        }
        return this.failCount;
    }

    public Integer clearFailCount() {
        this.failCount = null;

        if (this.isLocked = true) {
            this.isLocked = false;
        }
        return this.failCount;
    }


    @Override
    public boolean isAccountNonExpired() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        
        if (employee.getEmployeeLeaveDate() == null) {
            // 퇴사날짜 없으면 만료안함
            return true;
        }
        // employeeLeaveDate를 Instant로 변환하고, 서울 시간대의 ZonedDateTime으로 변환
        ZonedDateTime leaveDateTime = employee.getEmployeeLeaveDate().toInstant()
                .atZone(ZoneId.of("Asia/Seoul"));

        // leaveDateTime이 now 이후이면 만료되지 않음, now 이전이면 만료됨
        return !leaveDateTime.isBefore(now);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getCompanyCode() {
        return this.employee.getCompanycode();
    }

    public boolean isMatchingCompanyCode(String companyCode) {
        return this.employee.isMatchingCompanyCode(companyCode);
    }

    // JPA 프록시 객체 생성을 위한 기본생성자
    protected AppUser() {}

    // 빌더 생성자 -> 빌더 객체로부터 값을 받아 초기화
    public AppUser(Builder builder) {
        this.employee = builder.employee;
        this.ROLES = builder.ROLES;
        this.statusCODE = builder.statusCODE;
        this.password = builder.password;
        this.PIN = builder.PIN;
        this.failCount = builder().failCount;
        this.isLocked = builder.isLocked;
    }

    public static class Builder {
        private Employee employee;
        private ROLES ROLES;
        private CODE statusCODE = CODE.INIT;
        private String password;
        private String PIN;
        private Integer failCount;
        private boolean isLocked = false;

        public Builder employee(Employee employee) {
            if (employee == null) {
                throw new IllegalArgumentException("Employee cannot be null");
            }
            this.employee = employee;
            return this;
        }

        public Builder role(ROLES ROLES) {
            if (ROLES == null) {
                throw new IllegalArgumentException("Role cannot be null");
            }
            this.ROLES = ROLES;
            return this;
        }

        public Builder statusCode(CODE statusCODE) {
            this.statusCODE = statusCODE;
            return this;
        }

        public Builder password(String password) {
            if (password == null) {
                throw new IllegalArgumentException("Password cannot be null");
            }
            this.password = password;
            return this;
        }

        public Builder PIN(String PIN) {
            if (ROLES == null) {
                throw new IllegalArgumentException("PIN cannot be null");
            }
            this.PIN = PIN;
            return this;
        }

        public Builder failCount(Integer failCount) {
            this.failCount = failCount;
            return this;
        }

        public Builder isLocked(boolean isLocked) {
            this.isLocked = isLocked;
            return this;
        }

        public AppUser build() {
            if (employee == null || ROLES == null || password == null) {
                throw new IllegalStateException("Cannot build User object, one or more required fields are not set");
            }
            return new AppUser(this);
        }
    }
    
    // 정적 팩토리 메서드
    // new Builder() 대신 클래스.builder() 처럼 사용해서 인스턴스를 만들기위해서
    public static Builder builder() {
        return new Builder();
    }
}
