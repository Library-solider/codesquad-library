package kr.codesquad.library.admin.domain.category;

import kr.codesquad.library.domain.category.Category;
import lombok.Getter;

@Getter
public class CategorySummary {

    private final Long id;
    private final String title;

    private CategorySummary(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static CategorySummary from(Category category) {
        return new CategorySummary(category.getId(), category.getTitle());
    }
}
