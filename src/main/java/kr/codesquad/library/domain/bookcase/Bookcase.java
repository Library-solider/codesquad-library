package kr.codesquad.library.domain.bookcase;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Bookcase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookcase_id")
    public Long id;

    private String name;

    private String Location;
}
