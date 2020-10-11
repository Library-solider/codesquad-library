package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.common.PagingProperties;
import kr.codesquad.library.admin.domain.account.AccountAdminRepository;
import kr.codesquad.library.admin.domain.account.response.AccountDataResponse;
import kr.codesquad.library.admin.domain.account.response.AccountDetailsResponse;
import kr.codesquad.library.admin.domain.account.AccountSummary;
import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.LibraryRole;
import kr.codesquad.library.global.error.exception.domain.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static kr.codesquad.library.admin.common.ConstantsCoveringMagicNumber.ADMIN_PAGE_SIZE;
import static kr.codesquad.library.admin.common.ConstantsCoveringMagicNumber.MINIMUM_PAGE_NUMBER;

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
        Page<Account> accounts = accountAdminRepository.findAll(PageRequest.of(validatePageNumber(page), ADMIN_PAGE_SIZE));
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

    private int validatePageNumber(int page) {
        if (page < MINIMUM_PAGE_NUMBER) { page = MINIMUM_PAGE_NUMBER; }
        return page - 1;
    }
}
