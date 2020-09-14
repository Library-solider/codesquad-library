package kr.codesquad.library.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.codesquad.library.domain.account.response.AccountMyPageResponse;
import kr.codesquad.library.domain.account.response.AccountProfileResponse;
import kr.codesquad.library.global.api.ApiResult;
import kr.codesquad.library.global.config.oauth.dto.AccountPrincipal;
import kr.codesquad.library.global.config.resolver.LoginAccount;
import kr.codesquad.library.global.error.ErrorCode;
import kr.codesquad.library.global.error.ErrorResponse;
import kr.codesquad.library.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kr.codesquad.library.global.api.ApiResult.OK;

@Slf4j
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

    // 로그아웃상태 예외처리
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ErrorResponse> handleAccountException(final Exception exception) {
        log.info("LogoutException : {}", exception.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.ACCOUNT_LOGOUT);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
