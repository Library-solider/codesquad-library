package kr.codesquad.library.global.config.oauth.security;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.AccountRepository;
import kr.codesquad.library.domain.account.LibraryRole;
import kr.codesquad.library.global.config.oauth.dto.OAuthAttributes;
import kr.codesquad.library.global.config.oauth.dto.AccountPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        return processOAuth2User(registrationId, userNameAttributeName, oAuth2User.getAttributes());
    }

    private OAuth2User processOAuth2User(String registrationId, String userNameAttributeName,
                                         Map<String, Object> attributes) {
        OAuthAttributes userInfo = OAuthAttributes.of(registrationId, userNameAttributeName, attributes);
        Account account = saveOrUpdate(userInfo);

        return AccountPrincipal.builder()
                .id(account.getId())
                .oauthId(account.getOauthId())
                .name(account.getName())
                .email(account.getEmail())
                .avatarUrl(account.getAvatarUrl())
                .authorities(Collections.singleton(new SimpleGrantedAuthority(account.getRoleKey())))
                .attributes(attributes)
                .build();
    }

    private Account saveOrUpdate(OAuthAttributes userInfo) {
        Account account = accountRepository.findByOauthId(userInfo.getOauthId())
                .map(entity -> entity.update(userInfo.getName(), userInfo.getAvatarUrl()))
                .orElse(registerNewAccount(userInfo));

        return accountRepository.save(account);
    }

    private Account registerNewAccount(OAuthAttributes userInfo) {
        return Account.builder()
                .oauthId(userInfo.getOauthId())
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .avatarUrl(userInfo.getAvatarUrl())
                .libraryRole(LibraryRole.GUEST)
                .build();
    }
}
