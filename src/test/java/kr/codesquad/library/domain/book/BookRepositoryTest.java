package kr.codesquad.library.domain.book;

import kr.codesquad.library.domain.category.Category;
import kr.codesquad.library.domain.category.CategoryRepository;
import kr.codesquad.library.global.error.exception.domain.BookNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@TestPropertySource(locations = "classpath:application.yml")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category setup() {
        Category category = Category.builder()
                .id(1L)
                .title("모바일")
                .build();
        categoryRepository.save(category);

        return category;
    }

    @Transactional
    @DisplayName("모바일 서적을 검색하여 가져온다.")
    @CsvSource({"1, title, description, author, publisher, 2020-07-20, image, isbn123, true, 1"})
    @ParameterizedTest
    public void 첫번째카테고리_도서를_가져온다(Long id, String title, String description, String author, String publisher,
                                 LocalDate localDate, String image, String isbn, boolean available, int recommend) {

        Category mobile = setup();
        Book book = Book.builder()
                .id(id)
                .title(title)
                .description(description)
                .author(author)
                .publisher(publisher)
                .publicationDate(localDate)
                .imageUrl(image)
                .isbn(isbn)
                .available(available)
                .recommendCount(recommend)
                .build();
        book.setCategoryToTest(mobile);

        bookRepository.save(book);

        List<Book> MobileBooks = bookRepository.findTop6ByCategoryIdAndImageUrlIsNotNullOrderByRecommendCountDesc(1L);
        assertThat(MobileBooks.get(0).getTitle()).isEqualTo(book.getTitle());
    }

    @CsvSource({"0, 20, 1, publicationDate"})
    @ParameterizedTest
    public void 첫번째_카테고리페이지가져오기(int page, int size, Long categoryId, String property) {
        PageRequest pageRequest = getPageRequest(page, size, property);

        Page<Book> page1 = bookRepository.findAllByCategoryId(categoryId, pageRequest);

        assertThat(page1.getTotalElements()).isEqualTo(39);
        assertThat(page1.getTotalPages()).isEqualTo(2);
        assertThat(page1.getNumber()).isEqualTo(page);
        assertThat(page1.getSize()).isEqualTo(size);
    }

    @CsvSource({"0, 20, publicationDate, 롯데"})
    @ParameterizedTest
    public void 검색_실패(int page, int size, String property, String title) throws Exception {
        //when
        Page<Book> bookPage = bookRepository.findAllByTitleIgnoreCaseContainingOrAuthorIgnoreCaseContaining(title, title, getPageRequest(page, size, property));
        List<Book> bookList = bookPage.getContent();
        //then
        assertThat(bookList).isEmpty();
    }

    @CsvSource({"0, 20, publicationDate, 켄트 벡, 3"})
    @ParameterizedTest
    public void 도서제목_저자_검색(int page, int size, String property, String title, int bookCount) throws Exception {
        //when
        Page<Book> bookPage = bookRepository.findAllByTitleIgnoreCaseContainingOrAuthorIgnoreCaseContaining(title, title, getPageRequest(page, size, property));
        List<Book> bookList = bookPage.getContent();

        //then
        assertAll(
                () -> assertThat(bookList).isNotEmpty(),
                () -> assertThat(bookList.size()).isEqualTo(bookCount)
        );
    }

    @CsvSource({"1, 5, 11"})
    @ParameterizedTest
    public void 도서1번_상세페이지_가져오기(Long bookId, Long categoryId, Long bookCaseId) {
        //when
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);

        //then
        assertAll(
                () -> assertThat(book.getId()).isEqualTo(bookId),
                () -> assertThat(book.getCategory().getId()).isEqualTo(categoryId),
                () -> assertThat(book.getBookcase().getId()).isEqualTo(bookCaseId)
        );
    }

    private PageRequest getPageRequest(int page, int size, String property) {
        Sort sort = Sort.by(Sort.Direction.DESC, property);
        return PageRequest.of(page, size, sort);
    }
}
