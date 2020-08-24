package kr.codesquad.library.global.config.oauth.dto;

import java.util.Map;

public class GithubOAuth2UserInfo {

    private Map<String, Object> attributes;

    private String name;
    private String email;
    private String avatarUrl;

    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public String getName() {
        return (String) attributes.get("name");
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }

    public String getAvatarUrl() {
        return (String) attributes.get("avatar_url");
    }
}
