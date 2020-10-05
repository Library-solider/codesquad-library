package kr.codesquad.library.admin.domain.book.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class BookFormRequest {

    private final String title;
    private final String description;
    private final String author;
    private final String publisher;
    private final String isbn;
    private final String imageUrl;
    private final Long categoryId;
    private final Long bookcaseId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate publicationDate;
}
