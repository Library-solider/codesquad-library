package kr.codesquad.library.admin.domain.book.response;

import kr.codesquad.library.admin.common.PagingProperties;
import kr.codesquad.library.admin.domain.book.BookSummary;
import lombok.Getter;

import java.util.List;

@Getter
public class BookSummaryResponse {

    private final List<BookSummary> bookSummaries;
    private final PagingProperties pagingProperties;

    private BookSummaryResponse(List<BookSummary> bookSummaries, PagingProperties pagingProperties) {
        this.bookSummaries = bookSummaries;
        this.pagingProperties = pagingProperties;
    }

    public static BookSummaryResponse of(List<BookSummary> bookSummaries, PagingProperties pagingProperties) {
        return new BookSummaryResponse(bookSummaries, pagingProperties);
    }
}
