package com.librarycodesquad.admin.domain.account.response;

import com.librarycodesquad.prod.domain.account.Account;
import com.librarycodesquad.prod.domain.account.LibraryRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountDetailsResponse {

    private final Long id;
    private final Long oauthId;
    private final String name;
    private final String email;
    private final LibraryRole role;
    private final String avatarUrl;

    @Builder
    private AccountDetailsResponse(Long id, Long oauthId, String name, String email,
                                  LibraryRole role, String avatarUrl) {
        this.id = id;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.avatarUrl = avatarUrl;
    }

    public static AccountDetailsResponse from(Account account) {
        return AccountDetailsResponse.builder()
                                     .id(account.getId())
                                     .oauthId(account.getOauthId())
                                     .name(account.getName())
                                     .email(account.getEmail())
                                     .role(account.getLibraryRole())
                                     .avatarUrl(account.getAvatarUrl())
                                     .build();
    }

}
