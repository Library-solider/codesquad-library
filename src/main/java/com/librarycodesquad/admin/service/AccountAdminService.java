package com.librarycodesquad.admin.service;

import com.librarycodesquad.admin.common.ConstantsCoveringMagicNumber;
import com.librarycodesquad.admin.common.PagingProperties;
import com.librarycodesquad.admin.domain.account.AccountAdminRepository;
import com.librarycodesquad.admin.domain.account.response.AccountDataResponse;
import com.librarycodesquad.prod.global.error.exception.domain.AccountNotFoundException;
import com.librarycodesquad.admin.domain.account.response.AccountDetailsResponse;
import com.librarycodesquad.admin.domain.account.AccountSummary;
import com.librarycodesquad.prod.domain.account.Account;
import com.librarycodesquad.prod.domain.account.LibraryRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AccountAdminService {

    private final AccountAdminRepository accountAdminRepository;

    public List<AccountSummary> findAllAccountsByRole(LibraryRole role) {
        List<Account> accounts = accountAdminRepository.findAllByLibraryRole(role);
        return accounts.stream()
                       .map(AccountSummary::from)
                       .collect(Collectors.toList());
    }

    @Transactional
    public void changeAllAccountRoleById(List<Long> accountIds, LibraryRole libraryRole) {
        List<Account> accounts = accountAdminRepository.findAllById(accountIds);
        accounts.forEach(account -> account.changeRole(libraryRole));
    }

    public AccountDataResponse findAllAccounts(int page) {
        Page<Account> accounts = accountAdminRepository.findAll(PageRequest.of(validatePageNumber(page), ConstantsCoveringMagicNumber.ADMIN_PAGE_SIZE));
        PagingProperties pagingProperties = PagingProperties.from(accounts);
        List<Account> accountEntities = accounts.getContent();
        List<AccountSummary> accountSummaries = accountEntities.stream()
                                                               .map(AccountSummary::from)
                                                               .collect(Collectors.toList());
        return AccountDataResponse.of(accountSummaries, pagingProperties);
    }

    public AccountDetailsResponse findAccountDetails(Long accountId) {
        Account account = accountAdminRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        return AccountDetailsResponse.from(account);
    }

    public AccountDataResponse searchAccounts(int page, String name) {
        PageRequest pageRequest = PageRequest.of(validatePageNumber(page), ConstantsCoveringMagicNumber.ADMIN_PAGE_SIZE);
        Page<Account> accounts = accountAdminRepository.findAllByNameContainingIgnoreCase(pageRequest, name);
        PagingProperties pagingProperties = PagingProperties.from(accounts);
        List<Account> accountEntities = accounts.getContent();
        List<AccountSummary> accountSummaries = accountEntities.stream()
                                                               .map(AccountSummary::from)
                                                               .collect(Collectors.toList());
        return AccountDataResponse.of(accountSummaries, pagingProperties);
    }

    private int validatePageNumber(int page) {
        if (page < ConstantsCoveringMagicNumber.MINIMUM_PAGE_NUMBER) { page = ConstantsCoveringMagicNumber.MINIMUM_PAGE_NUMBER; }
        return page - 1;
    }
}
