package com.librarycodesquad.prod.controller;

import com.librarycodesquad.prod.domain.account.response.AccountMyPageResponse;
import com.librarycodesquad.prod.domain.account.response.AccountProfileResponse;
import com.librarycodesquad.prod.global.api.ApiResult;
import com.librarycodesquad.prod.global.config.oauth.dto.AccountPrincipal;
import com.librarycodesquad.prod.global.config.resolver.LoginAccount;
import com.librarycodesquad.prod.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.librarycodesquad.prod.global.api.ApiResult.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/mypage")
    public ApiResult<AccountMyPageResponse> getMyPage(@LoginAccount AccountPrincipal loginAccount) {
        return OK(accountService.getMyPage(loginAccount.getId()));
    }

    @GetMapping("/profile")
    public ApiResult<AccountProfileResponse> getProfile(@LoginAccount AccountPrincipal loginAccount) {
        return OK(accountService.getProfile(loginAccount.getId()));
    }

    @GetMapping("/role")
    public ApiResult<Boolean> checkAccountRole(@LoginAccount AccountPrincipal loginAccount) {
        return OK(accountService.checkRole(loginAccount.getId()));
    }
}
