package kr.codesquad.library.domain.book;

import kr.codesquad.library.domain.category.Category;
import kr.codesquad.library.domain.category.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository books;

    @Autowired
    private CategoryRepository categories;

    private Category setup() {
        Category category = Category.builder()
                .id(1L)
                .title("모바일")
                .build();
        categories.save(category);

        return category;
    }

    @Transactional
    @DisplayName("모바일 서적을 검색하여 가져온다.")
    @CsvSource({"1, title, description, author, publisher, 2020-07-20, image, isbn123, true, 1"})
    @ParameterizedTest
    public void 첫번째카테고리_도서를_가져온다(Long id, String title, String description, String author, String publisher,
                                 LocalDate localDate, String image, String isbn, Boolean inStock, Integer recommend) {

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
                .outOfStock(inStock)
                .recommendCount(recommend)
                .build();
        book.setCategory(mobile);

        books.save(book);

        List<Book> MobileBooks = books.findTop6bookByCategoryIdOrderByRecommendCountDesc(1L);
        assertThat(MobileBooks.get(0).getTitle()).isEqualTo(book.getTitle());
    }
}
