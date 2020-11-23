package com.librarycodesquad.prod.domain.account.response;

import com.librarycodesquad.prod.domain.account.Account;
import com.librarycodesquad.prod.domain.account.LibraryRole;
import com.librarycodesquad.prod.domain.rental.response.RentalBookResponse;
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

    public static AccountMyPageResponse of(Account account, List<RentalBookResponse> rentalBookResponses) {
        return AccountMyPageResponse.builder()
                .name(account.getName())
                .email(account.getEmail())
                .avatarUrl(account.getAvatarUrl())
                .role(account.getLibraryRole())
                .rentalBookResponses(rentalBookResponses)
                .build();
    }
}
