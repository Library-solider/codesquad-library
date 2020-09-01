package kr.codesquad.library.domain.account.response;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.rental.response.RentalBookResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AccountMyPageResponse {

    private final String name;
    private final String email;
    private final String avatarUrl;
    private final List<RentalBookResponse> rentalBookResponse;

    @Builder
    private AccountMyPageResponse(String name, String email, String avatarUrl, List<RentalBookResponse> rentalBookResponse) {
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.rentalBookResponse = rentalBookResponse;
    }

    public static AccountMyPageResponse of(Account account, List<RentalBookResponse> rentalBookResponse) {
        return AccountMyPageResponse.builder()
                .name(account.getName())
                .email(account.getEmail())
                .avatarUrl(account.getAvatarUrl())
                .rentalBookResponse(rentalBookResponse)
                .build();
    }
}
