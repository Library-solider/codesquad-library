package com.librarycodesquad.prod.controller;

import com.librarycodesquad.prod.global.config.resolver.LoginAccount;
import com.librarycodesquad.prod.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.librarycodesquad.prod.domain.account.response.AccountMyPageResponse;
import com.librarycodesquad.prod.domain.account.response.AccountProfileResponse;
import com.librarycodesquad.prod.global.api.ApiResult;
import com.librarycodesquad.prod.global.config.oauth.dto.AccountPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.librarycodesquad.prod.global.api.ApiResult.OK;

@Api
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class AccountController {

    private final AccountService accountService;

    @ApiOperation(value = "마이페이지")
    @GetMapping("/mypage")
    public ApiResult<AccountMyPageResponse> getMyPage(@LoginAccount AccountPrincipal loginAccount) {
        return OK(accountService.getMyPage(loginAccount.getId()));
    }

    @ApiOperation(value = "프로필")
    @GetMapping("/profile")
    public ApiResult<AccountProfileResponse> getProfile(@LoginAccount AccountPrincipal loginAccount) {
        return OK(accountService.getProfile(loginAccount.getId()));
    }
}
