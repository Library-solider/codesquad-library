package com.librarycodesquad.prod.domain.bookcase;

import com.librarycodesquad.prod.domain.book.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Bookcase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookcase_id")
    private Long id;

    private String location;

    @OneToMany(mappedBy = "bookcase")
    private List<Book> books = new ArrayList<>();

    private Bookcase(String location) {
        this.location = location;
    }

    public Bookcase changeLocation(String location) {
        this.location = location;
        return this;
    }

    public boolean hasAnyBooks() {
        return !books.isEmpty();
    }

    public static Bookcase from(String location) {
        return new Bookcase(location);
    }
}
