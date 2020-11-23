package com.librarycodesquad.prod.service;

import com.librarycodesquad.prod.domain.rental.RentalRepository;
import com.librarycodesquad.prod.global.error.exception.domain.AccountNotFoundException;
import com.librarycodesquad.prod.domain.account.Account;
import com.librarycodesquad.prod.domain.account.AccountRepository;
import com.librarycodesquad.prod.domain.account.response.AccountMyPageResponse;
import com.librarycodesquad.prod.domain.account.response.AccountProfileResponse;
import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.rental.Rental;
import com.librarycodesquad.prod.domain.rental.response.RentalBookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final RentalRepository rentalRepository;

    public AccountMyPageResponse getMyPage(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        List<Rental> rentalList = rentalRepository.findAllByAccountAndIsReturnedFalse(account);
        List<Book> bookList = rentalList.stream().map(Rental::getBook).collect(Collectors.toList());
        List<RentalBookResponse> rentalBookResponses = new ArrayList<>();

        for (int i = 0; i < rentalList.size(); i++) {
            rentalBookResponses.add(RentalBookResponse.of(bookList.get(i), rentalList.get(i)));
        }

        return AccountMyPageResponse.of(account, rentalBookResponses);
    }

    public AccountProfileResponse getProfile(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        return AccountProfileResponse.from(account);
    }
}
