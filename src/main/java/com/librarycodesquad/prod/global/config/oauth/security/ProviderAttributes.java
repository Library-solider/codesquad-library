package com.librarycodesquad.prod.global.config.oauth.security;

import com.librarycodesquad.prod.global.error.exception.oauth.ProviderNotExistException;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum ProviderAttributes {
    GITHUB("github", "id", "name", "email", "avatar_url");

    private final String registrationId;
    private final String oauthId;
    private final String name;
    private final String email;
    private final String avatarUrl;

    ProviderAttributes(String registrationId, String oauthId, String name, String email, String avatarUrl) {
        this.registrationId = registrationId;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    public static ProviderAttributes of( String registrationId) {
        return Stream.of(values())
                .filter(providerAttributes -> providerAttributes.registrationId.equals(registrationId.toLowerCase()))
                .findFirst()
                .orElseThrow(ProviderNotExistException::new);
    }
}
