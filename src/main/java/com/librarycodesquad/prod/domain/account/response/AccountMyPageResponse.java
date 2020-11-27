package com.librarycodesquad.prod.domain.account.response;

import com.librarycodesquad.prod.domain.account.Account;
import com.librarycodesquad.prod.domain.account.LibraryRole;
import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.rental.Rental;
import com.librarycodesquad.prod.domain.rental.response.RentalBookResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AccountMyPageResponse {

    private final String name;
    private final String email;
    private final String avatarUrl;
    private final LibraryRole role;
    private final List<RentalBookResponse> rentalBookResponses;

    @Builder
    private AccountMyPageResponse(String name, String email, String avatarUrl, LibraryRole role,
                                  List<Rental> rentalList) {
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.role = role;
        this.rentalBookResponses = getRentalBooks(rentalList);
    }

    public static AccountMyPageResponse of(Account account, List<Rental> rentalList) {
        return AccountMyPageResponse.builder()
                .name(account.getName())
                .email(account.getEmail())
                .avatarUrl(account.getAvatarUrl())
                .role(account.getLibraryRole())
                .rentalList(rentalList)
                .build();
    }

    private List<RentalBookResponse> getRentalBooks(List<Rental> rentals) {
        return rentals.stream()
                .map(rental -> {
                    Book book = rental.getBook();
                    return RentalBookResponse.of(book, rental);
                }).collect(Collectors.toList());
    }
}
