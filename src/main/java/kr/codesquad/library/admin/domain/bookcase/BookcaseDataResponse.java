package kr.codesquad.library.admin.domain.bookcase;

import kr.codesquad.library.admin.common.PagingProperties;
import kr.codesquad.library.admin.domain.book.BookSummary;
import kr.codesquad.library.admin.domain.category.CategorySummary;
import kr.codesquad.library.domain.book.Book;
import kr.codesquad.library.domain.bookcase.Bookcase;
import kr.codesquad.library.domain.category.Category;
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
