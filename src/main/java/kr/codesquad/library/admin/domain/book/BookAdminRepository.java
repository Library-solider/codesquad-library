package kr.codesquad.library.admin.domain.book;

import kr.codesquad.library.domain.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookAdminRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT b FROM Book b JOIN FETCH b.category ORDER BY b.id",
           countQuery = "SELECT COUNT(b) FROM Book b INNER JOIN b.category")
    Page<Book> findAllWithCategory(Pageable pageable);
}
