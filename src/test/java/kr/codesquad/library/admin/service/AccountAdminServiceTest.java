package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.account.AccountAdminRepository;
import kr.codesquad.library.admin.domain.account.AccountDetailsResponse;
import kr.codesquad.library.admin.domain.account.AccountSummaryResponse;
import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.LibraryRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @CsvSource({"1, 2, 3, 3"})
    @ParameterizedTest
    public void ROLE이_GUEST인_Account를_가져온다(Long firstGuestAccountId, Long secondGuestAccountId, Long thirdGuestAccountId,
                                              int countOfGuestAccount) {
        //given
        List<Long> guestAccountIds = Arrays.asList(firstGuestAccountId, secondGuestAccountId, thirdGuestAccountId);

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
        accountAdminService.changeAllAccountRoleById(guestAccountIds, LibraryRole.USER);
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

    @CsvSource({"1, 8"})
    @ParameterizedTest
    public void 모든_회원_목록을_조회한다(int defaultPageNumber, int countOfAllAccounts) {
        //when
        List<AccountSummaryResponse> accounts = accountAdminService.findAllAccounts();

        //then
        assertThat(accounts.size()).isEqualTo(countOfAllAccounts);
    }

    @CsvSource({"1, 11111, HONUX, honux77@github.com, http://img.honux, GUEST"})
    @ParameterizedTest
    public void 회원의_상세정보를_조회한다(Long accountId, Long oauthId, String name, String email, String avatarUrl, LibraryRole role) {
        //when
        AccountDetailsResponse accountDetails = accountAdminService.findAccountDetails(accountId);

        //then
        assertAll(
                () -> assertThat(accountDetails.getId()).isEqualTo(accountId),
                () -> assertThat(accountDetails.getOauthId()).isEqualTo(oauthId),
                () -> assertThat(accountDetails.getName()).isEqualTo(name),
                () -> assertThat(accountDetails.getEmail()).isEqualTo(email),
                () -> assertThat(accountDetails.getAvatarUrl()).isEqualTo(avatarUrl),
                () -> assertThat(accountDetails.getRole()).isEqualTo(role)
        );
    }
}
