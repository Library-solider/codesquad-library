package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.account.AccountAdminRepository;
import kr.codesquad.library.admin.domain.account.AccountDetailsResponse;
import kr.codesquad.library.admin.domain.account.AccountSummaryResponse;
import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.LibraryRole;
import kr.codesquad.library.global.error.exception.domain.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AccountAdminService {

    private final AccountAdminRepository accountAdminRepository;

    public List<AccountSummaryResponse> findAllAccountsByRole(LibraryRole role) {
        List<Account> accounts = accountAdminRepository.findAllByLibraryRole(role);
        return accounts.stream()
                       .map(AccountSummaryResponse::from)
                       .collect(Collectors.toList());
    }

    @Transactional
    public void changeAllAccountRoleById(List<Long> accountIds, LibraryRole libraryRole) {
        List<Account> accounts = accountAdminRepository.findAllById(accountIds);
        accounts.forEach(account -> account.changeRole(libraryRole));
    }

    public List<AccountSummaryResponse> findAllAccounts() {
        List<Account> accounts = accountAdminRepository.findAll();
        return accounts.stream()
                       .map(AccountSummaryResponse::from)
                       .collect(Collectors.toList());
    }

    public AccountDetailsResponse findAccountDetails(Long accountId) {
        Account account = accountAdminRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        return AccountDetailsResponse.from(account);
    }
}
