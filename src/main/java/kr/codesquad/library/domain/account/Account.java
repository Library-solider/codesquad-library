package kr.codesquad.library.domain.account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.*;

@Getter
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "oauth_id", nullable = false, unique = true)
    private Long oauthId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "library_role", nullable = false)
    @Enumerated(STRING)
    private LibraryRole libraryRole;

    @Builder
    private Account(Long oauthId, String name, String email, LibraryRole libraryRole, String avatarUrl) {
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.libraryRole = libraryRole;
        this.avatarUrl = avatarUrl;
    }

    public void changeRole(LibraryRole libraryRole) {
        this.libraryRole = libraryRole;
    }

    public Account update(String name, String avatarUrl) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        return this;
    }

    public String getRoleKey() {
        return this.libraryRole.getKey();
    }
}
