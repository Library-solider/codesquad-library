package kr.codesquad.library.global.config.oauth.dto;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.LibraryRole;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String name;
    private final String email;
    private final String avatarUrl;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
                           String name, String email, String avatarUrl) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    public static OAuthAttributes of(String usernameAttribute, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name(String.valueOf(attributes.get("name")))
                .email(String.valueOf(attributes.get("email")))
                .avatarUrl(String.valueOf(attributes.get("avatar_url")))
                .attributes(attributes)
                .nameAttributeKey(usernameAttribute)
                .build();
    }

    public Account toEntity() {
        return Account.builder()
                .name(name)
                .email(email)
                .libraryRole(LibraryRole.GUEST)
                .avatarUrl(avatarUrl)
                .build();
    }

}
