package kr.codesquad.library.admin.model.account;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.LibraryRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountSummary {

    private Long id;

    private Long oauthId;

    private String name;

    private String email;

    private LibraryRole role;

    @Builder
    private AccountSummary(Long id, Long oauthId, String name, String email, LibraryRole role) {
        this.id = id;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public static AccountSummary of(Account account) {
        return AccountSummary.builder()
                          .id(account.getId())
                          .oauthId(account.getOauthId())
                          .name(account.getName())
                          .email(account.getEmail())
                          .role(account.getLibraryRole())
                          .build();
    }
}
