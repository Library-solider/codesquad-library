package kr.codesquad.library.domain.account.response;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.LibraryRole;
import kr.codesquad.library.domain.rental.response.RentalBookResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AccountMyPageResponse {

    private final String name;
    private final String email;
    private final String avatarUrl;
    private final LibraryRole role;
    private final List<RentalBookResponse> rentalBookResponses;

    @Builder
    private AccountMyPageResponse(String name, String email, String avatarUrl, LibraryRole role,
                                  List<RentalBookResponse> rentalBookResponses) {
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.role = role;
        this.rentalBookResponses = rentalBookResponses;
    }

    public static AccountMyPageResponse from(Account account, List<RentalBookResponse> rentalBookResponses) {
        return AccountMyPageResponse.builder()
                .name(account.getName())
                .email(account.getEmail())
                .avatarUrl(account.getAvatarUrl())
                .role(account.getLibraryRole())
                .rentalBookResponses(rentalBookResponses)
                .build();
    }
}
