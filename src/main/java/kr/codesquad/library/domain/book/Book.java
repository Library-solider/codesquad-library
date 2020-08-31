package kr.codesquad.library.domain.book;

import kr.codesquad.library.domain.category.Category;
import kr.codesquad.library.domain.rental.Rental;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

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

    private boolean available;

    @Column(name = "recommend_count")
    private int recommendCount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "book", cascade = ALL)
    private List<Rental> rentals = new ArrayList<>();

    @Builder
    private Book(Long id, String title, String description, String author, String publisher,
                 LocalDate publicationDate, String imageUrl, String isbn, boolean available, int recommendCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.imageUrl = imageUrl;
        this.isbn = isbn;
        this.available = available;
        this.recommendCount = recommendCount;
    }

    public void rentalOrReturnBook() {
        this.available = !available;
    }

    public void setCategoryToTest(Category category) {
        this.category = category;
    }
}
