package kr.codesquad.library.admin.domain.category;

import kr.codesquad.library.domain.category.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryDetailResponse {

    private final Long id;
    private final String title;

    @Builder
    private CategoryDetailResponse(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static CategoryDetailResponse from(Category category) {
        return CategoryDetailResponse.builder()
                                     .id(category.getId())
                                     .title(category.getTitle())
                                     .build();
    }
}
