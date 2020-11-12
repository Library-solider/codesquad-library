package com.librarycodesquad.admin.domain.category;

import com.librarycodesquad.prod.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryAdminRepository extends JpaRepository<Category, Long> {
}
