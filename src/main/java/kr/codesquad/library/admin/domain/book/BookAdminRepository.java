package kr.codesquad.library.admin.domain.book;

import kr.codesquad.library.domain.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookAdminRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT b FROM Book b JOIN FETCH b.category JOIN FETCH b.bookcase ORDER BY b.id DESC",
           countQuery = "SELECT COUNT(b) FROM Book b INNER JOIN b.category INNER JOIN b.bookcase")
    Page<Book> findAllFetch(Pageable pageable);

    @Query(value ="SELECT b FROM Book b JOIN FETCH b.category WHERE b.category.id = :categoryId ORDER BY b.id DESC",
           countQuery = "SELECT COUNT(b) FROM Book b INNER JOIN b.category WHERE b.category.id = :categoryId")
    Page<Book> findAllByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query(value = "SELECT b FROM Book b JOIN FETCH b.bookcase WHERE b.bookcase.id = :bookcaseId ORDER BY b.id DESC",
            countQuery = "SELECT COUNT(b) FROM Book b INNER JOIN b.bookcase WHERE b.bookcase.id = :bookcaseId")
    Page<Book> findAllByBookcaseId(@Param("bookcaseId") Long bookcaseId, Pageable pageable);

    List<Book> findAllByIdIn(List<Long> bookIds);

    Page<Book> findAllByTitleContainingIgnoreCase(Pageable pageable, String title);
}
