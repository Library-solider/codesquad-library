package kr.codesquad.library.admin.domain.administrator;

import kr.codesquad.library.domain.account.LibraryRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Administrator {

    @Id
    @Column(name = "admin_name")
    private String adminName;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "library_role")
    private LibraryRole libraryRole;
}
