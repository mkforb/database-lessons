package org.jjd.lessons.jpa.enity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by User on 07.06.2021.
 */
@Entity
public class BookAuthor {

    @EmbeddedId
    private BookAuthorKey key;

    // В таблице book - одна запись, в таблице book_author - много
    @ManyToOne
    // При составном первичном ключе надо указать название поля в составном ключе
    @MapsId("bookId")
    private Book book;

    @ManyToOne
    @MapsId("authorId")
    private Author author;

    @Embeddable
    @EqualsAndHashCode
    public static class BookAuthorKey implements Serializable {

        @Getter
        @Column(name = "book_id")
        private int bookId;

        @Getter
        @Column(name = "author_id")
        private int authorId;

    }

}
