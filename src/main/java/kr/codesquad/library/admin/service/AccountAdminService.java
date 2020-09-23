package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.account.AccountAdminRepository;
import kr.codesquad.library.admin.domain.account.AccountSummaryResponse;
import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.LibraryRole;
import lombok.RequiredArgsConstructor;
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
                       .map(AccountSummaryResponse::of)
                       .collect(Collectors.toList());
    }
}
