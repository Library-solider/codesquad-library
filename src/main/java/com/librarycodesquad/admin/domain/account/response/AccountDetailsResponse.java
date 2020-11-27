package com.librarycodesquad.admin.domain.account.response;

import com.librarycodesquad.admin.domain.book.AccountRentalBook;
import com.librarycodesquad.prod.domain.account.Account;
import com.librarycodesquad.prod.domain.account.LibraryRole;
import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.rental.Rental;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AccountDetailsResponse {

    private final Long id;
    private final Long oauthId;
    private final String name;
    private final String email;
    private final LibraryRole role;
    private final String avatarUrl;
    private final List<AccountRentalBook> accountRentalBooks;

    @Builder
    private AccountDetailsResponse(Long id, Long oauthId, String name, String email,
                                   LibraryRole role, String avatarUrl, List<Rental> rentals) {
        this.id = id;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.avatarUrl = avatarUrl;
        this.accountRentalBooks = getAccountRentalBooks(rentals);
    }

    public static AccountDetailsResponse of(Account account, List<Rental> rentals) {
        return AccountDetailsResponse.builder()
                .id(account.getId())
                .oauthId(account.getOauthId())
                .name(account.getName())
                .email(account.getEmail())
                .role(account.getLibraryRole())
                .avatarUrl(account.getAvatarUrl())
                .rentals(rentals)
                .build();
    }

    private List<AccountRentalBook> getAccountRentalBooks(List<Rental> rentals) {
        return rentals.stream()
                .map(rental -> {
                    Book book = rental.getBook();
                    return AccountRentalBook.of(book, rental);
                }).collect(Collectors.toList());
    }
}
