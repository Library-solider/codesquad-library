package kr.codesquad.library.admin.model.account;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.LibraryRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountAdminRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByLibraryRole(LibraryRole libraryRole);
}
