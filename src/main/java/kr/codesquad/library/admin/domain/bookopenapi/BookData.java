package kr.codesquad.library.admin.domain.bookopenapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
public class BookData {

    private String title;
    private String description;
    private String author;
    private String publisher;
    private String isbn;

    @JsonProperty("coverLargeUrl")
    private String imageUrl;

    @JsonProperty("pubDate")
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate publicationDate;
}
