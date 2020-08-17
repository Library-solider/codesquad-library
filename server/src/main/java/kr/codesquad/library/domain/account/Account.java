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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "library_role", nullable = false, columnDefinition = "varchar(64) default 'GUEST'")
    @Enumerated(STRING)
    private LibraryRole libraryRole;

    @Builder
    public Account(String name, String email, LibraryRole libraryRole) {
        this.name = name;
        this.email = email;
        this.libraryRole = libraryRole;
    }

    public Account changeRole(LibraryRole libraryRole) {
        this.libraryRole = libraryRole;
        return this;
    }

    public Account update(String name) {
        this.name = name;
        return this;
    }

    public String getRoleKey() {
        return this.libraryRole.getKey();
    }
}
