package kr.codesquad.library.admin.domain.account;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.LibraryRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountSummary {

    private final Long id;
    private final Long oauthId;
    private final String name;
    private final String email;
    private final LibraryRole role;

    @Builder
    private AccountSummary(Long id, Long oauthId, String name, String email, LibraryRole role) {
        this.id = id;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public static AccountSummary from(Account account) {
        return AccountSummary.builder()
                                     .id(account.getId())
                                     .oauthId(account.getOauthId())
                                     .name(account.getName())
                                     .email(account.getEmail())
                                     .role(account.getLibraryRole())
                                     .build();
    }
}
