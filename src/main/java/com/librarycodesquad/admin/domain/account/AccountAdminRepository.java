package com.librarycodesquad.admin.domain.account;

import com.librarycodesquad.prod.domain.account.Account;
import com.librarycodesquad.prod.domain.account.LibraryRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountAdminRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByLibraryRole(LibraryRole libraryRole);

    Page<Account> findAll(Pageable pageable);

    Page<Account> findAllByNameContainingIgnoreCase(Pageable pageable, String name);
}
