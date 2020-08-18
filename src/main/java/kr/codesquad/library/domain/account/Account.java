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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "library_role", nullable = false)
    @Enumerated(STRING)
    private LibraryRole libraryRole;

    @Builder
    public Account(String name, String email, LibraryRole libraryRole) {
        this.name = name;
        this.email = email;
        this.libraryRole = libraryRole;
    }

    public void changeRole(LibraryRole libraryRole) {
        this.libraryRole = libraryRole;
    }

    public Account updateName(String name) {
        this.name = name;
        return this;
    }

    public String getRoleKey() {
        return this.libraryRole.getKey();
    }
}
