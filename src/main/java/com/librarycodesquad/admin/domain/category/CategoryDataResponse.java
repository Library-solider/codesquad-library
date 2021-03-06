package com.librarycodesquad.admin.domain.category;

import com.librarycodesquad.admin.common.PagingProperties;
import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.category.Category;
import com.librarycodesquad.admin.domain.book.BookSummary;
import com.librarycodesquad.admin.domain.bookcase.BookcaseSummary;
import com.librarycodesquad.prod.domain.bookcase.Bookcase;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CategoryDataResponse {

    private final CategorySummary category;
    private final List<BookSummary> books;
    private final List<CategorySummary> categories;
    private final List<BookcaseSummary> bookcases;
    private final PagingProperties pagingProperties;

    @Builder
    public CategoryDataResponse(Category category, List<Book> books, List<Category> categories,
                                List<Bookcase> bookcases, PagingProperties pagingProperties) {
        this.category = mapToCategory(category);
        this.books = mapToBookSummaries(books);
        this.categories = mapToCategorySummaries(categories);
        this.bookcases = mapToBookcaseSummaries(bookcases);
        this.pagingProperties = pagingProperties;
    }

    private CategorySummary mapToCategory(Category category) {
        return CategorySummary.from(category);
    }

    private List<BookSummary> mapToBookSummaries(List<Book> books) {
        return books.stream()
                    .map(BookSummary::from)
                    .collect(Collectors.toList());
    }

    private List<CategorySummary> mapToCategorySummaries(List<Category> categories) {
        return categories.stream()
                         .map(CategorySummary::from)
                         .collect(Collectors.toList());
    }

    private List<BookcaseSummary> mapToBookcaseSummaries(List<Bookcase> bookcases) {
        return bookcases.stream()
                        .map(BookcaseSummary::from)
                        .collect(Collectors.toList());
    }
}
