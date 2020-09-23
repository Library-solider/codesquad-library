package kr.codesquad.library.admin.controller;

import kr.codesquad.library.admin.domain.account.AccountSummaryResponse;
import kr.codesquad.library.admin.service.AccountAdminService;
import kr.codesquad.library.domain.account.LibraryRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AccountAdminController {

    private final AccountAdminService accountAdminService;

    @GetMapping("")
    public String authorize() {
        return "authorization";
    }

    @GetMapping("/main")
    public String main(Model model) {
        List<AccountSummaryResponse> accountSummaries = accountAdminService.findAllAccountsByRole(LibraryRole.GUEST);
        model.addAttribute("accountSummaries", accountSummaries);
        return "index";
    }
}
