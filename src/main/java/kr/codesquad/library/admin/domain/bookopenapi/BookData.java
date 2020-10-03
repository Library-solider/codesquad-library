package kr.codesquad.library.admin.domain.bookopenapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class BookData {

    private String title;
    private String description;
    private String author;
    private String publisher;

    @JsonProperty("coverLargeUrl")
    private String imageUrl;
}
