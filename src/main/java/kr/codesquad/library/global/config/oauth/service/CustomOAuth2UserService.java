package kr.codesquad.library.global.config.oauth.service;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.AccountRepository;
import kr.codesquad.library.domain.account.LibraryRole;
import kr.codesquad.library.global.config.oauth.dto.AccountPrincipal;
import kr.codesquad.library.global.config.oauth.dto.GithubOAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        return processOAuth2User(oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2User oAuth2User) {
        GithubOAuth2UserInfo userInfo = new GithubOAuth2UserInfo(oAuth2User.getAttributes());
        Account account = saveOrUpdate(userInfo);


        return AccountPrincipal.create(account, userInfo.getAttributes());
    }

    private Account saveOrUpdate(GithubOAuth2UserInfo userInfo) {
        Account account = accountRepository.findByEmail(userInfo.getEmail())
                .map(entity -> entity.update(userInfo.getName(), userInfo.getEmail()))
                .orElse(registerNewAccount(userInfo));

        return accountRepository.save(account);
    }

    private Account registerNewAccount(GithubOAuth2UserInfo userInfo) {
        return Account.builder()
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .avatarUrl(userInfo.getAvatarUrl())
                .libraryRole(LibraryRole.GUEST)
                .build();
    }
}
