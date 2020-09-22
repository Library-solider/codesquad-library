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
}
