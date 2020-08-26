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
public class SecurityUser implements OAuth2User {

    private final Long id;
    private final Long oauthId;
    private final String name;
    private final String email;
    private final String avatarUrl;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;

    @Builder
    private SecurityUser(Long id, Long oauthId, String name, String email, String avatarUrl,
                         Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.id = id;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    public static SecurityUser create(Account account, Map<String, Object> attributes) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(account.getRoleKey()));

        return SecurityUser.builder()
                .id(account.getId())
                .oauthId(account.getOauthId())
                .name(account.getName())
                .email(account.getEmail())
                .avatarUrl(account.getAvatarUrl())
                .authorities(authorities)
                .attributes(attributes)
                .build();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}
