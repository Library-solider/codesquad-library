package kr.codesquad.library.domain.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByCategoryId(Long categoryId, Pageable pageable);

    List<Book> findTop6ByCategoryIdAndImageUrlIsNotNullOrderByRecommendCountDesc(Long categoryId);

    Page<Book> findByTitleContaining(String title, Pageable pageable);
}
