package com.librarycodesquad.admin.domain.bookcase;

import com.librarycodesquad.prod.domain.bookcase.Bookcase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookcaseAdminRepository extends JpaRepository<Bookcase, Long> {
}
