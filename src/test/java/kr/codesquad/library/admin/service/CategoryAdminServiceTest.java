package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.category.CategoryDetail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CategoryAdminServiceTest {

    @Autowired
    CategoryAdminService categoryAdminService;

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

}
