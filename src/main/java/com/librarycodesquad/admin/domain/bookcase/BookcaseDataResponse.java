package com.librarycodesquad.admin.domain.bookcase;

import com.librarycodesquad.admin.common.PagingProperties;
import com.librarycodesquad.admin.domain.category.CategorySummary;
import com.librarycodesquad.prod.domain.book.Book;
import com.librarycodesquad.prod.domain.category.Category;
import com.librarycodesquad.admin.domain.book.BookSummary;
import com.librarycodesquad.prod.domain.bookcase.Bookcase;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BookcaseDataResponse {

    private final BookcaseSummary bookcase;
    private final List<BookSummary> books;
    private final List<CategorySummary> categories;
    private final List<BookcaseSummary> bookcases;
    private final PagingProperties pagingProperties;

    @Builder
    public BookcaseDataResponse(Bookcase bookcase, List<Book> books, List<Category> categories,
                                List<Bookcase> bookcases, PagingProperties pagingProperties) {
        this.bookcase = mapToCategory(bookcase);
        this.books = mapToBookSummaries(books);
        this.categories = mapToCategorySummaries(categories);
        this.bookcases = mapToBookcaseSummaries(bookcases);
        this.pagingProperties = pagingProperties;
    }

    private BookcaseSummary mapToCategory(Bookcase bookcase) {
        return BookcaseSummary.from(bookcase);
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
