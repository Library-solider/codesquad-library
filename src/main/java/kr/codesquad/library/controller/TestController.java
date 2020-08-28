package kr.codesquad.library.controller;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.AccountRepository;
import kr.codesquad.library.domain.account.response.AccountProfileResponse;
import kr.codesquad.library.global.config.oauth.dto.AccountPrincipal;
import kr.codesquad.library.global.config.resolver.LoginUser;
import kr.codesquad.library.global.error.exception.domain.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TestController {

    private final AccountRepository accountRepository;

    @GetMapping("/")
    public String login() {

        return "index";
    }

    @GetMapping("/v1/users/mypage")
    public ResponseEntity<AccountProfileResponse> getProfile(@LoginUser AccountPrincipal user) {
        log.info(">>>> SecurityUser : {}", user);
        Account account = accountRepository.findById(user.getId()).orElseThrow(AccountNotFoundException::new);
        AccountProfileResponse profileResponse = AccountProfileResponse.of(account);

        return ResponseEntity.ok(profileResponse);
    }
}
