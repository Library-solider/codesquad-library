package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.model.account.AccountAdminRepository;
import kr.codesquad.library.admin.model.account.AccountSummary;
import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.LibraryRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountAdminService {

    private final AccountAdminRepository accountAdminRepository;

    public List<AccountSummary> findAllAccountsByRole(LibraryRole role) {
        List<Account> accounts = accountAdminRepository.findAllByLibraryRole(role);
        return accounts.stream()
                       .map(AccountSummary::of)
                       .collect(Collectors.toList());
    }
}
