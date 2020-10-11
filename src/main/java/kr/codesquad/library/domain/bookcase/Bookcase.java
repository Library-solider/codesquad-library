package kr.codesquad.library.domain.bookcase;

import kr.codesquad.library.domain.book.Book;
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
}
