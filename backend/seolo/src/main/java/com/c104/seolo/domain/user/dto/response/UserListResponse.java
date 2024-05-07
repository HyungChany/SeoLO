package com.c104.seolo.domain.user.dto.response;

import com.c104.seolo.domain.user.dto.info.UserListInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserListResponse {
    private List<UserListInfo> workers;

    @Builder
    public UserListResponse(List<UserListInfo> workers) {
        this.workers = workers;
    }
}
