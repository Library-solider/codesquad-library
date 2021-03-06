package com.librarycodesquad.prod.domain.category;

import com.librarycodesquad.prod.domain.book.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String title;

    @OneToMany(mappedBy = "category")
    List<Book> books = new ArrayList<>();

    private Category(String title) {
        this.title = title;
    }

    @Builder
    private Category(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Category changeTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean hasAnyBooks() {
        return !books.isEmpty();
    }

    public static Category from(String title) {
        return new Category(title);
    }
}
