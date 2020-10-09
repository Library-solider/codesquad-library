package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.book.BookSummary;
import kr.codesquad.library.admin.domain.bookcase.BookcaseSummary;
import kr.codesquad.library.admin.domain.category.CategoryAdminRepository;
import kr.codesquad.library.admin.domain.category.CategoryDataResponse;
import kr.codesquad.library.admin.domain.category.CategoryDetail;
import kr.codesquad.library.admin.domain.category.CategorySummary;
import kr.codesquad.library.domain.category.Category;
import kr.codesquad.library.global.error.exception.domain.CategoryNotFoundException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class CategoryAdminServiceTest {

    @Autowired
    CategoryAdminService categoryAdminService;

    @Autowired
    CategoryAdminRepository categoryAdminRepository;

    @CsvSource({"5, 3, 64"})
    @ParameterizedTest
    public void 모든_카테고리를_조회한다(int categoryCount, Long categoryId, int bookCount) {
        //when
        List<CategoryDetail> categories = categoryAdminService.findAllCategory();
        CategoryDetail categoryDetail = categories.stream()
                                                  .filter(category -> category.getId().equals(categoryId))
                                                  .findAny()
                                                  .orElse(null);

        //then
        assertThat(categories.size()).isEqualTo(categoryCount);
        assertThat(categoryDetail.getBookCount()).isEqualTo(bookCount);
    }

    @CsvSource({"1, 3, 10, 5, 3"})
    @ParameterizedTest
    public void 특정_카테고리를_도서와_함께_조회한다(Long categoryId, int page, int bookCount,
                                             int categoryCount, int bookcaseCount) {
        //when
        CategoryDataResponse categoryData = categoryAdminService.findCategoryDataById(categoryId, page);
        CategorySummary category = categoryData.getCategory();
        List<BookSummary> books = categoryData.getBooks();
        List<CategorySummary> categories = categoryData.getCategories();
        List<BookcaseSummary> bookcases = categoryData.getBookcases();

        //then
        assertAll(
                () -> assertThat(category.getId()).isEqualTo(categoryId),
                () -> assertThat(books.size()).isEqualTo(10),
                () -> assertThat(categories.size()).isEqualTo(categoryCount),
                () -> assertThat(bookcases.size()).isEqualTo(bookcaseCount)
        );
    }

    @CsvSource({"새 카테고리, 6"})
    @ParameterizedTest
    public void 새로운_카테고리를_등록한다(String title, int categoryCount) {
        //when
        Long categoryId = categoryAdminService.createNewCategory(title);
        Category newCategory = categoryAdminRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        List<Category> categories = categoryAdminRepository.findAll();

        //then
        assertAll(
                () -> assertThat(categoryId).isEqualTo(newCategory.getId()),
                () -> assertThat(categories.size()).isEqualTo(categoryCount)
        );

    }
}
