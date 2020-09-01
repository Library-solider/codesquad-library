package kr.codesquad.library.controller;

import kr.codesquad.library.domain.account.response.AccountMyPageResponse;
import kr.codesquad.library.global.api.ApiResult;
import kr.codesquad.library.global.config.oauth.dto.AccountPrincipal;
import kr.codesquad.library.global.config.resolver.LoginAccount;
import kr.codesquad.library.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kr.codesquad.library.global.api.ApiResult.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/users/mypage")
    public ApiResult<AccountMyPageResponse> getProfile(@LoginAccount AccountPrincipal loginAccount) {
        return OK(accountService.getMyPage(loginAccount.getId()));
    }
}
