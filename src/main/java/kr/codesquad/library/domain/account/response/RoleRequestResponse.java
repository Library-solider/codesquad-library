package kr.codesquad.library.domain.account.response;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.LibraryRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RoleRequestResponse {
    private final String name;
    private final LibraryRole role;
    private final boolean requested;

    @Builder
    private RoleRequestResponse(String name, LibraryRole role, boolean requested) {
        this.name = name;
        this.role = role;
        this.requested = requested;
    }

    public static RoleRequestResponse of(Account account, boolean requested) {
        return RoleRequestResponse.builder()
                .name(account.getName())
                .role(account.getLibraryRole())
                .requested(requested)
                .build();
    }
}
