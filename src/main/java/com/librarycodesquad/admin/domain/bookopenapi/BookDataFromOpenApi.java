package com.librarycodesquad.admin.domain.bookopenapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.librarycodesquad.admin.domain.book.BookData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BookDataFromOpenApi {

    @JsonProperty("item")
    private final List<BookData> bookData = new ArrayList<>();

}
