package kr.codesquad.library.admin.domain.account;

import kr.codesquad.library.domain.account.Account;
import kr.codesquad.library.domain.account.LibraryRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AccountAdminRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByLibraryRole(LibraryRole libraryRole);

    Page<Account> findAll(Pageable pageable);
}
