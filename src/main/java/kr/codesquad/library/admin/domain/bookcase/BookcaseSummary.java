package kr.codesquad.library.admin.domain.bookcase;

import kr.codesquad.library.domain.bookcase.Bookcase;
import lombok.Getter;

@Getter
public class BookcaseSummary {

    private final Long id;
    private final String location;

    private BookcaseSummary(Long id, String location) {
        this.id = id;
        this.location = location;
    }

    public static BookcaseSummary from(Bookcase bookcase) {
        return new BookcaseSummary(bookcase.getId(), bookcase.getLocation());
    }

}
