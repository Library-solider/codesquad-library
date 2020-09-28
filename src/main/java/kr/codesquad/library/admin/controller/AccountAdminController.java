package kr.codesquad.library.admin.controller;

import kr.codesquad.library.admin.domain.account.AccountSummaryResponse;
import kr.codesquad.library.admin.service.AccountAdminService;
import kr.codesquad.library.domain.account.LibraryRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/admin/users")
public class AccountAdminController {

    private final AccountAdminService accountAdminService;

    @GetMapping("")
    public String findAll(Model model) {
        List<AccountSummaryResponse> accountSummaries = accountAdminService.findAllAccounts();
        model.addAttribute("accountSummaries", accountSummaries);
        return "account/all-accounts";
    }

    @GetMapping("/guest")
    public String guestAccounts(Model model) {
        List<AccountSummaryResponse> accountSummaries = accountAdminService.findAllAccountsByRole(LibraryRole.GUEST);
        model.addAttribute("accountSummaries", accountSummaries);
        return "account/guest-accounts";
    }

    @PostMapping("/role")
    @ResponseStatus(HttpStatus.OK)
    public void authorizeAccount(@RequestBody List<Long> accountIds) {
        log.debug("accountIds ::: {}", accountIds);
        accountAdminService.changeAllAccountRoleById(accountIds, LibraryRole.USER);
    }
}
