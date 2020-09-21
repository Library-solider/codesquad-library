package kr.codesquad.library.service;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.AccountRepository;
import kr.codesquad.library.domain.account.response.AccountMyPageResponse;
import kr.codesquad.library.domain.account.response.AccountProfileResponse;
import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.rental.Rental;
import kr.codesquad.library.domain.rental.RentalRepository;
import kr.codesquad.library.domain.rental.response.RentalBookResponse;
import kr.codesquad.library.global.error.exception.domain.AccountNotFoundException;
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
        return AccountProfileResponse.of(account);
    }
}
