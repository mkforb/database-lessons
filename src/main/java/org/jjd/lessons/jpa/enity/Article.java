package org.jjd.lessons.jpa.enity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by User on 04.06.2021.
 */
@Entity
@Table(name = "tb_articles")
public class Article {

    @Getter
    @Setter
    @EmbeddedId // Указывает на составной ключ
    private ArticleKey key;

    @Getter
    @Setter
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    // 1. Составной ПК опиывается аннотацией @Embeddable
    // 2. Класс должен реализовывать ИФ Serializable
    // 3. У класса должны переопределены equals() и hashCode()
    @Embeddable
    @EqualsAndHashCode
    public static class ArticleKey implements Serializable {
        // Поля составного первичного ключа
        @Getter
        @Setter
        @Column(length = 30)
        private String name;

        @Getter
        @Setter
        @Column(length = 100)
        private String surname;
    }
}
