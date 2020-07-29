package kr.codesquad.library.domain.book.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MainBookResponse {

    private final MobileBooksResponse mobileBooks;
    private final FrontendBooksResponse frontendBooks;
    private final BackendBooksResponse backendBooks;
    private final ITBooksResponse itBooks;
    private final CultureBooksResponse cultureBooks;

    @Builder
    public MainBookResponse(MobileBooksResponse mobileBooks,
                            FrontendBooksResponse frontendBooks,
                            BackendBooksResponse backendBooks,
                            ITBooksResponse itBooks,
                            CultureBooksResponse cultureBooks) {
        this.mobileBooks = mobileBooks;
        this.frontendBooks = frontendBooks;
        this.backendBooks = backendBooks;
        this.itBooks = itBooks;
        this.cultureBooks = cultureBooks;
    }
}
