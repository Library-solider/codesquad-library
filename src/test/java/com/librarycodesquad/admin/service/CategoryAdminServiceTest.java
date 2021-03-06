package com.librarycodesquad.admin.service;

import com.librarycodesquad.admin.domain.book.BookSummary;
import com.librarycodesquad.admin.domain.bookcase.BookcaseSummary;
import com.librarycodesquad.admin.domain.category.CategoryAdminRepository;
import com.librarycodesquad.admin.domain.category.CategoryDataResponse;
import com.librarycodesquad.admin.domain.category.CategoryDetail;
import com.librarycodesquad.admin.domain.category.CategorySummary;
import com.librarycodesquad.prod.domain.category.Category;
import com.librarycodesquad.prod.global.error.exception.domain.CategoryNotFoundException;
import com.librarycodesquad.prod.global.error.exception.admin.DeleteEntityDeniedException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @CsvSource({"1, 새제목"})
    @ParameterizedTest
    public void 카테고리를_수정한다(Long categoryId, String title) {
        //when
        Long updatedCategoryId = categoryAdminService.updateCategoryTitle(categoryId, title);
        Category updatedCategory = categoryAdminRepository.findById(updatedCategoryId).orElseThrow(CategoryNotFoundException::new);

        //then
        assertThat(updatedCategory.getTitle()).isEqualTo(title);
    }

    @CsvSource({"9, 1"})
    @ParameterizedTest
    public void 카테고리를_삭제한다(Long noBooksCategoryId, Long booksCategoryId) {
        //given
        List<Category> categories = categoryAdminRepository.findAll();
        int currentCount = categories.size();


        //when
        categoryAdminService.deleteCategory(noBooksCategoryId);
        List<Category> newCategories = categoryAdminRepository.findAll();
        int afterCount = newCategories.size();

        //then
        assertAll(
                () -> assertThrows(DeleteEntityDeniedException.class, () -> categoryAdminService.deleteCategory(booksCategoryId)),
                () -> assertThat(currentCount).isEqualTo(afterCount + 1)
        );
    }
}
