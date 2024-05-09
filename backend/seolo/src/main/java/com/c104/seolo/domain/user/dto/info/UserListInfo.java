package com.c104.seolo.domain.user.dto.info;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

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
}
