package kr.codesquad.library.admin.domain.bookcase;

import kr.codesquad.library.domain.bookcase.Bookcase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookcaseAdminRepository extends JpaRepository<Bookcase, Long> {
}
