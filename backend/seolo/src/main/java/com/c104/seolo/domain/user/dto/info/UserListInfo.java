package com.c104.seolo.domain.user.dto.info;

import com.c104.seolo.domain.user.entity.AppUser;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserListInfo {
    private Long id;
    private String name;
    private String title;
    private String team;
    private String role;

    @Builder
    public UserListInfo(Long id, String name, String titles, String teams, String role) {
        this.id = id;
        this.name = name;
        this.title = titles;
        this.team = teams;
        this.role = role;
    }

    public static UserListInfo of(AppUser appUser) {
        return UserListInfo.builder()
                .id(appUser.getId())
                .name(appUser.getUsername())
                .titles(appUser.getUserTitle())
                .teams(appUser.getUserTeam())
                .role(appUser.getROLES().name())
                .build();
    }

    public static UserListInfo ofButTranslateRoleToKorean(AppUser appUser) {
        Map<String, String> roleMap = new HashMap<>();
        roleMap.put("ROLE_MANAGER", "관리자");
        roleMap.put("ROLE_WORKER", "작업자");
        String translatedRole;
        try {
            translatedRole = roleMap.getOrDefault(appUser.getROLES().name(), "알 수 없음");
        } catch (Exception e) {
            translatedRole = "알 수 없음";
        }
        return UserListInfo.builder()
                .id(appUser.getId())
                .name(appUser.getUsername())
                .titles(appUser.getUserTitle())
                .teams(appUser.getUserTeam())
                .role(translatedRole)
                .build();
    }
}
