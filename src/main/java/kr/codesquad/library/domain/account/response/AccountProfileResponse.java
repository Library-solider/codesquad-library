package kr.codesquad.library.domain.account.response;

import kr.codesquad.library.domain.account.Account;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountProfileResponse {

    private final String name;
    private final String email;
    private final String avatarUrl;

    @Builder
    private AccountProfileResponse(String name, String email, String avatarUrl) {
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    public static AccountProfileResponse of(Account account) {
        return AccountProfileResponse.builder()
                .name(account.getName())
                .email(account.getEmail())
                .avatarUrl(account.getAvatarUrl())
                .build();
    }
}
