package kr.codesquad.library.controller;

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

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleAccountException(final Exception exception) {
        log.info("LogoutException : {}", exception.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.ACCOUNT_LOGOUT);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
