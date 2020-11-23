package com.librarycodesquad.admin.domain.bookcase;

import com.librarycodesquad.prod.domain.bookcase.Bookcase;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookcaseDetail {

    private final Long id;
    private final String location;
    private final int bookCount;

    @Builder
    private BookcaseDetail(Long id, String location, int bookCount) {
        this.id = id;
        this.location = location;
        this.bookCount = bookCount;
    }

    public static BookcaseDetail of(Bookcase bookcase, int bookCount) {
        return BookcaseDetail.builder()
                             .id(bookcase.getId())
                             .location(bookcase.getLocation())
                             .bookCount(bookCount)
                             .build();
    }
}
