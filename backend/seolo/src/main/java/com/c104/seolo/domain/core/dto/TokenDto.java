package com.c104.seolo.domain.core.dto;

import com.c104.seolo.domain.core.entity.Token;
import com.c104.seolo.domain.user.entity.AppUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    private String tokenValue;
    private LockerDto locker;


    @Builder
    public TokenDto(String tokenValue, LockerDto locker) {
        this.tokenValue = tokenValue;
        this.locker = locker;
    }

    public static TokenDto of(Token token) {
        return TokenDto.builder()
                .tokenValue(token.getTokenValue())
                .locker(LockerDto.of(token.getLocker()))
                .build();
    }

    public Token toEntity() {
        return Token.builder()
                .tokenValue(tokenValue)
                .locker(locker.toEntity())
                .build();
    }
}