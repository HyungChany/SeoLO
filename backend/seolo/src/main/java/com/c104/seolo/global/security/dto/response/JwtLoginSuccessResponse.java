package com.c104.seolo.global.security.dto.response;

import com.c104.seolo.global.security.jwt.dto.response.IssuedToken;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtLoginSuccessResponse {
    private final Long userId;
    private final String username;
    private final String companyCode;
    private final String codeStatus;
    private final IssuedToken issuedToken;
    private  String workingLockerUid;
    private  Long workingMachineId;
    private  String issuedCoreToken;

    @Builder
    public JwtLoginSuccessResponse(Long userId, String username, String companyCode, String codeStatus, IssuedToken issuedToken, String workingLockerUid, Long workingMachineId, String issuedCoreToken) {
        this.userId = userId;
        this.username = username;
        this.companyCode = companyCode;
        this.codeStatus = codeStatus;
        this.issuedToken = issuedToken;
        this.workingLockerUid = workingLockerUid;
        this.workingMachineId = workingMachineId;
        this.issuedCoreToken = issuedCoreToken;
    }
}
