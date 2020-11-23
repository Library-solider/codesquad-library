package com.librarycodesquad.prod.global.config.oauth.dto;

import com.librarycodesquad.prod.global.config.oauth.security.ProviderAttributes;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private final String nameAttributeKey;
    private final Long oauthId;
    private final String name;
    private final String email;
    private final String avatarUrl;
    private final Map<String, Object> attributes;

    @Builder
    public OAuthAttributes(String nameAttributeKey, Long oauthId, String name, String email, String avatarUrl,
                           Map<String, Object> attributes) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.attributes = attributes;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {
        ProviderAttributes provider = ProviderAttributes.of(registrationId);

        return OAuthAttributes.builder()
                .oauthId(Long.valueOf((Integer) attributes.get(provider.getOauthId())))
                .name((String) attributes.get(provider.getName()))
                .email((String) attributes.get(provider.getEmail()))
                .avatarUrl((String) attributes.get(provider.getAvatarUrl()))
                .nameAttributeKey(userNameAttributeName)
                .attributes(attributes)
                .build();
    }
}
