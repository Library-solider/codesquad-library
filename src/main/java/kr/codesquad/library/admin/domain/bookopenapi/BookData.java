package kr.codesquad.library.admin.domain.bookopenapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
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
    private String publicationDate;
}
