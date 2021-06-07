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
public class Event extends Parent {

    @Getter
    @Setter
    @Column(nullable = false)
    private String title;

    @Getter
    @Setter
    private LocalDate start;

    @Getter
    @Setter
    // mappedBy для ManyToMany и OneToOne достаточно написать с одной стороны
    @ManyToMany
    @JoinTable(name = "tb_students_events", // можно дать свое название таблице и столбцам
            joinColumns = @JoinColumn(name = "event_id"), // связь текущей сущности с таблицей связи
            inverseJoinColumns = @JoinColumn(name = "student_id")) // связь другой сущности с таблицей связи
    private List<Student> students = new ArrayList<>();

}
