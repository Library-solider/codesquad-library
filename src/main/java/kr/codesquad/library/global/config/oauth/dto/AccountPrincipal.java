package kr.codesquad.library.global.config.oauth.dto;

import kr.codesquad.library.domain.account.Account;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public class AccountPrincipal implements OAuth2User {

    private final Long id;
    private final String name;
    private final String email;
    private final String avatarUrl;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Builder
    private AccountPrincipal(Long id, String name, String email, String avatarUrl,
                            Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    public static AccountPrincipal create(Account account, Map<String, Object> attributes) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(account.getRoleKey()));

        return AccountPrincipal.builder()
                .id(account.getId())
                .name(account.getName())
                .email(account.getEmail())
                .avatarUrl(account.getAvatarUrl())
                .authorities(authorities)
                .attributes(attributes)
                .build();
    }
}
