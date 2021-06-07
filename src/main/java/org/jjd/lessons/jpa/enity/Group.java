package org.jjd.lessons.jpa.enity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 04.06.2021.
 */
@Entity
// "duration" - имя столбца в таблице
// Индексу можно присвоить имя
@Table(name = "tb_groups", indexes = @Index(columnList = "duration", name = "ind_name"))
// Именованные запросы
@NamedQueries({ // Используется синтаксис JPQL. Вместо таблиц и их столбцов используются названия классов и их полей
        // SQL: SELECT * FROM tb_groups;
        // JPQL: SELECT g FROM Group g; -- выбрать все поля
        // JPQL: SELECT g.title FROM Group g; -- выбрать поле title
        // name - имя запроса, определяем сами
        @NamedQuery(name = "group.get_all", query = "SELECT g FROM Group g"),
        @NamedQuery(name = "group.get_by_title", query = "SELECT g FROM Group g WHERE g.title = :group_title")
})
public class Group extends Parent {

    @Getter
    @Setter
    @Column(nullable = false)
    private String title;

    @Getter
    @Setter
    @Column(nullable = false, precision = 2, scale = 1)
    private double duration;

    @Getter
    @Setter
    @Column(nullable = false)
    private int price;

    @Getter
    @Setter
    private LocalDate start;

    @Getter
    @Setter
    // mappedBy - имя поля в классе Student, в которому студент связан с группой
    // orphanRemoval = true - если в коде удалить студента из коллекции, то он удалится из БД тоже. Надо быть осторожным!
    @OneToMany(mappedBy = "group", orphanRemoval = true) // fetch default LAZY, нужно ставить LAZY, иначе будут извлекаться все студенты
    private List<Student> students = new ArrayList<>(); // объект коллекции надо создать при объявлении или в конструкторе

}
