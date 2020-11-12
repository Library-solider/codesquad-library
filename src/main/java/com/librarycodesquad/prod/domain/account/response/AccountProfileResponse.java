package com.librarycodesquad.prod.domain.account.response;

import com.librarycodesquad.prod.domain.account.Account;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountProfileResponse {

    private final String name;
    private final String avatarUrl;

    @Builder
    private AccountProfileResponse(String name, String avatarUrl) {
        this.name = name;
        this.avatarUrl = avatarUrl;
    }

    public static AccountProfileResponse from(Account account) {
        return AccountProfileResponse.builder()
                .name(account.getName())
                .avatarUrl(account.getAvatarUrl())
                .build();
    }
}
