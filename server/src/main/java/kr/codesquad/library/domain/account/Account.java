package kr.codesquad.library.domain.account;

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

    @Column(name = "github_token", nullable = false)
    private String githubToken;

    @Column(name = "library_role", nullable = false, columnDefinition = "varchar(64) default 'GUEST'")
    @Enumerated(STRING)
    private LibraryRole libraryRole;
}
