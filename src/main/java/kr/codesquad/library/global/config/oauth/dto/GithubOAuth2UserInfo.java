package kr.codesquad.library.global.config.oauth.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class GithubOAuth2UserInfo {

    private final Long oauthId;
    private final String name;
    private final String email;
    private final String avatarUrl;
    private final Map<String, Object> attributes;

    @Builder
    public GithubOAuth2UserInfo(Long oauthId, String name, String email, String avatarUrl, Map<String, Object> attributes) {
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.attributes = attributes;
    }

    public static GithubOAuth2UserInfo of(Map<String, Object> attributes) {
        return GithubOAuth2UserInfo.builder()
                .oauthId(Long.valueOf((Integer) attributes.get("id")))
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .avatarUrl((String) attributes.get("avatar_url"))
                .attributes(attributes)
                .build();
    }
}
