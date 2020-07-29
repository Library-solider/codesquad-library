package kr.codesquad.library.domain.book;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String title;

    @Column(length = 2048)
    private String description;
    private String author;
    private String publisher;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "image_url", length = 512)
    private String imageUrl;

    private String isbn;

    @Column(name = "out_of_stock")
    private Boolean outOfStock;

    @Column(name = "recommend_count")
    private Integer recommendCount;

    public void addImageAndDescription(String imageUrl, String description) {
        this.imageUrl = imageUrl;
        this.description = description;
    }
}
