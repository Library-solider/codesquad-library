package kr.codesquad.library.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findTop6BookByCategoryId(Long categoryId);
}
