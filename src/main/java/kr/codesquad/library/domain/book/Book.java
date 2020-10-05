package kr.codesquad.library.domain.book;

import kr.codesquad.library.admin.domain.book.request.BookFormRequest;
import kr.codesquad.library.domain.bookcase.Bookcase;
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bookcase_id")
    private Bookcase bookcase;

    @Builder
    private Book(Long id, String title, String description, String author, String publisher,
                 LocalDate publicationDate, String imageUrl, String isbn,
                 Category category, boolean available, int recommendCount, Bookcase bookcase) {
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
        this.category = category;
        this.bookcase = bookcase;
    }

    public void rentalBook() {
        this.available = false;
    }

    public void returnBook() {
        this.available = true;
    }

    public void setCategoryToTest(Category category) {
        this.category = category;
    }

    public Long updateBook(BookFormRequest bookFormRequest, Category category, Bookcase bookcase) {
        this.title = bookFormRequest.getTitle();
        this.description = bookFormRequest.getDescription();
        this.author = bookFormRequest.getAuthor();
        this.publisher = bookFormRequest.getPublisher();
        this.publicationDate = bookFormRequest.getPublicationDate();
        this.imageUrl = bookFormRequest.getImageUrl();
        this.isbn = bookFormRequest.getIsbn();
        this.category = changeCategory(category);
        this.bookcase = changeBookcase(bookcase);
        return this.id;
    }

    public Category changeCategory(Category category) {
        if (isDifferentCategory(category)) {
            this.category.getBooks().add(this);
            return category;
        }
        return category;
    }

    public Bookcase changeBookcase(Bookcase bookcase) {
        this.bookcase = bookcase;
        return bookcase;
    }

    private boolean isDifferentCategory(Category category) {
        return this.category.equals(category);
    }

    public static Book of(BookFormRequest bookFormRequest, Category category, Bookcase bookcase) {
        return Book.builder()
                   .title(bookFormRequest.getTitle())
                   .description(bookFormRequest.getDescription())
                   .author(bookFormRequest.getAuthor())
                   .publisher(bookFormRequest.getPublisher())
                   .publicationDate(bookFormRequest.getPublicationDate())
                   .imageUrl(bookFormRequest.getImageUrl())
                   .isbn(bookFormRequest.getIsbn())
                   .available(true)
                   .recommendCount(0)
                   .category(category)
                   .bookcase(bookcase)
                   .build();
    }
}
