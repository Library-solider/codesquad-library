package kr.codesquad.library.admin.domain.book;

import kr.codesquad.library.domain.book.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookAdminRepositoryTest {

    @Autowired
    BookAdminRepository bookAdminRepository;

    @CsvSource({"1, 10, 0, 1, 컴퓨터공학/IT, 9791185890678"})
    @ParameterizedTest
    public void 모든_도서정보를_카테고리와_함께_조회한다(int page, int pageSize, int index,
                                                Long bookId, String categoryTitle, String isbn) {
        //when
        Page<Book> books = bookAdminRepository.findAllWithCategory(PageRequest.of(page - 1, pageSize));
        List<Book> bookEntities = books.getContent();
        Book selectedBook = bookEntities.stream()
                                        .filter(book -> book.getId().equals(bookId))
                                        .collect(Collectors.toList())
                                        .get(index);

        //then
        assertAll(
                () -> assertThat(books.getSize()).isEqualTo(pageSize),
                () -> assertThat(selectedBook.getId()).isEqualTo(bookId),
                () -> assertThat(selectedBook.getCategory().getTitle()).isEqualTo(categoryTitle),
                () -> assertThat(selectedBook.getIsbn()).isEqualTo(isbn)
        );

    }

}