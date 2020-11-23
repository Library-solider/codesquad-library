package com.librarycodesquad.admin.domain.category;

import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.category.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryDetail {

    private final Long id;
    private final String title;
    private final int bookCount;

    @Builder
    private CategoryDetail(Long id, String title, int bookCount) {
        this.id = id;
        this.title = title;
        this.bookCount = bookCount;
    }

    public static CategoryDetail of(Category category, List<Book> books) {
        return CategoryDetail.builder()
                             .id(category.getId())
                             .title(category.getTitle())
                             .bookCount(books.size())
                             .build();
    }
}
