package kr.codesquad.library.domain.account;

import kr.codesquad.library.domain.rental.Rental;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
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

    @OneToMany(mappedBy = "account", cascade = ALL)
    private List<Rental> rentals = new ArrayList<>();

    @Builder
    private Account(Long oauthId, String name, String email, LibraryRole libraryRole, String avatarUrl, List<Rental> rentals) {
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.libraryRole = libraryRole;
        this.avatarUrl = avatarUrl;
        this.rentals = rentals;
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
        return libraryRole.getKey();
    }
}
