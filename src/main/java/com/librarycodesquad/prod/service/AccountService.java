package com.librarycodesquad.prod.service;

import com.librarycodesquad.prod.domain.account.Account;
import com.librarycodesquad.prod.domain.account.AccountRepository;
import com.librarycodesquad.prod.domain.account.response.AccountMyPageResponse;
import com.librarycodesquad.prod.domain.account.response.AccountProfileResponse;
import com.librarycodesquad.prod.domain.rental.Rental;
import com.librarycodesquad.prod.domain.rental.RentalRepository;
import com.librarycodesquad.prod.global.error.exception.domain.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final RentalRepository rentalRepository;

    public AccountMyPageResponse getMyPage(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        List<Rental> rentalList = rentalRepository.findAllByAccountAndIsReturnedFalse(account);

        return AccountMyPageResponse.of(account, rentalList);
    }

    public AccountProfileResponse getProfile(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        return AccountProfileResponse.from(account);
    }

    public boolean checkRole(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        return account.isUserRole();
    }
}
