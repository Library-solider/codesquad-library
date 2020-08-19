package kr.codesquad.library.global.config.oauth.service;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.AccountRepository;
import kr.codesquad.library.global.config.oauth.dto.OAuthAttributes;
import kr.codesquad.library.global.config.oauth.dto.SessionAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2Service {

    private final AccountRepository accountRepository;
    private final HttpSession httpSession;

    protected OAuth2User getUser(String userNameAttributeName, Map<String, Object> attributesMap) {
        OAuthAttributes attributes = OAuthAttributes.of(userNameAttributeName, attributesMap);
        Account account = saveOrUpdate(attributes);
        httpSession.setAttribute("account", new SessionAccount(account));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(account.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private Account saveOrUpdate(OAuthAttributes attributes) {
        Account account = accountRepository.findByEmail(attributes.getEmail())
                .map(entry -> entry.updateName(attributes.getName()))
                .orElseGet(attributes::toEntity);

        return accountRepository.save(account);
    }
}