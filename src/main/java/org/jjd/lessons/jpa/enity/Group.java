package org.jjd.lessons.jpa.enity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 04.06.2021.
 */
@Entity
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
