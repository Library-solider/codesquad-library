package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.account.AccountAdminRepository;
import kr.codesquad.library.admin.domain.account.AccountSummaryResponse;
import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.LibraryRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/*
*  [ROLE] = Account ID
*  [GUEST] = 1, 2, 3
*  [USER] = 4, 5, 6
*  [ADMIN] = 7, 8
* */
@SpringBootTest
@Transactional
class AccountAdminServiceTest {

    @Autowired
    private AccountAdminService accountAdminService;

    @Autowired
    private AccountAdminRepository accountAdminRepository;

    @Test
    public void ROLE이_GUEST인_Account를_가져온다() {
        //given
        int countOfGuestAccount = 3;
        List<Long> guestAccountIds = Arrays.asList(1L, 2L, 3L);

        //when
        List<AccountSummaryResponse> accountSummaries = accountAdminService.findAllAccountsByRole(LibraryRole.GUEST);

        //then
        assertEquals(accountSummaries.size(), countOfGuestAccount);
        assertEquals(accountSummaries.stream()
                                     .map(AccountSummaryResponse::getId)
                                     .collect(Collectors.toList()), guestAccountIds);
    }

    @CsvSource({"1, 2, 3, 0, 5"})
    @ParameterizedTest
    public void 회원에게_USER권한을_부여한다(Long firstGuestAccountId, Long secondGuestAccountId, Long thirdGuestAccountId,
                                        int guestRoleAccountsSizeAfterChangeRole,
                                        int userRoleAccountsSizeAfterChangeRole) {
        //given
        List<Long> guestAccountIds = Arrays.asList(firstGuestAccountId, secondGuestAccountId, thirdGuestAccountId);

        //when
        accountAdminService.authorizeAccount(guestAccountIds);
        List<Account> roleChangedAccounts = accountAdminRepository.findAllById(guestAccountIds);
        List<Account> guestRoleAccountsAfterChangeRole = accountAdminRepository.findAllByLibraryRole(LibraryRole.GUEST);
        List<Account> userRoleAccountsAfterChangeRole = accountAdminRepository.findAllByLibraryRole(LibraryRole.USER);

        //then
        assertAll(
                () -> assertTrue(roleChangedAccounts.stream().allMatch(account -> account.getLibraryRole().equals(LibraryRole.USER))),
                () -> assertThat(guestRoleAccountsAfterChangeRole.size()).isEqualTo(guestRoleAccountsSizeAfterChangeRole),
                () -> assertThat(userRoleAccountsAfterChangeRole.size()).isEqualTo(userRoleAccountsSizeAfterChangeRole)
        );

    }
}
