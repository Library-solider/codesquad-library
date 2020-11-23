package com.librarycodesquad.prod.global.config.oauth.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;
import java.util.Set;

@Getter
public class AccountPrincipal implements OAuth2User {

    private final Long id;
    private final Long oauthId;
    private final String name;
    private final String email;
    private final String avatarUrl;
    private final Set<GrantedAuthority> authorities;
    private final Map<String, Object> attributes;

    @Builder
    private AccountPrincipal(Long id, Long oauthId, String name, String email, String avatarUrl,
                             Set<GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.id = id;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}
