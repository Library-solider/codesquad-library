package kr.codesquad.library.domain.book;

import kr.codesquad.library.domain.book.response.BookResponse;
import kr.codesquad.library.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.*;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    private Book(Long id, String title, String description, String author, String publisher,
                 LocalDate publicationDate, String imageUrl, String isbn, Boolean outOfStock, Integer recommendCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.imageUrl = imageUrl;
        this.isbn = isbn;
        this.outOfStock = outOfStock;
        this.recommendCount = recommendCount;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BookResponse toResponse() {
        return BookResponse.builder()
                .id(id)
                .imageUrl(imageUrl)
                .title(title)
                .author(author)
                .publisher(publisher)
                .recommendCount(recommendCount)
                .publicationDate(publicationDate)
                .build();
    }

}
