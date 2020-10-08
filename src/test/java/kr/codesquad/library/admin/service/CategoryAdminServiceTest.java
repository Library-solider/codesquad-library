package kr.codesquad.library.admin.service;

import kr.codesquad.library.admin.domain.category.CategoryDetailResponse;
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

    @CsvSource({"5"})
    @ParameterizedTest
    public void 모든_카테고리를_조회한다(int categorySize) {
        //when
        List<CategoryDetailResponse> categories = categoryAdminService.findAllCategory();

        //then
        assertThat(categories.size()).isEqualTo(categorySize);
    }

}
