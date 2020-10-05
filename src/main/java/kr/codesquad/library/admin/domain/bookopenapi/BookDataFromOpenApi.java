package kr.codesquad.library.admin.domain.bookopenapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.codesquad.library.admin.domain.book.BookData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BookDataFromOpenApi {

    @JsonProperty("item")
    private final List<BookData> bookData = new ArrayList<>();

}
