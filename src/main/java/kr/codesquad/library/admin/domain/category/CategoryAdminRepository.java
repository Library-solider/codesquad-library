package kr.codesquad.library.admin.domain.category;

import kr.codesquad.library.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryAdminRepository extends JpaRepository<Category, Long> {
}
