package kr.codesquad.library.admin.domain.bookopenapi;

import kr.codesquad.library.domain.bookcase.Bookcase;
import kr.codesquad.library.domain.category.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BookWithRequiredFormDataResponse {

    private final BookData bookData;
    private final List<Category> categories;
    private final List<Bookcase> bookcases;

    @Builder
    private BookWithRequiredFormDataResponse(BookData bookData, List<Category> categories, List<Bookcase> bookcases) {
        this.bookData = bookData;
        this.categories = categories;
        this.bookcases = bookcases;
    }

    public static BookWithRequiredFormDataResponse of(BookData bookData, List<Category> categories, List<Bookcase> bookcases) {
        return BookWithRequiredFormDataResponse.builder()
                                               .bookData(bookData)
                                               .categories(categories)
                                               .bookcases(bookcases)
                                               .build();
    }
}
