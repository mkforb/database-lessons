package org.jjd.lessons.jpa.enity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 04.06.2021.
 */
// POJO или Java bean - классы, у которых есть свойства, геттеры, сеттеры, конструктор по умолчанию и иногда equals() и hashCode()
@Entity
public class Student extends Parent {

    @Getter
    @Setter
    @Column(nullable = false, length = 30)
    private String name;

    @Getter
    @Setter
    @Column(nullable = false, length = 100)
    private String surname;

    @Getter
    @Setter
    // fetch:
    // LAZY - информация о группе вытянется по запросу (getGroup())
    // EAGER - при выборке студента вытянется информация о группе
    // cascade - какие каскадные операции доступны:
    // PERSIST/MERGE - при добавлении студента будет каскадно добавлять/обновлять группу
    // REMOVE/DETACH - относятся к hybernate, каскадное удаление (REMOVE, не проверяет что на больше нет ссылающихся групп), отвязка от менеджера (DETACH)
    // REFRESH - обновит состояние группы из БД
    // ALL - все
    @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn // можно задать имя поля
    private Group group;

    @Getter
    @Setter
    // mappedBy - название поля из связянной сущности (в классе Event)
    @ManyToMany(mappedBy = "students") // fetch default LAZY, должен быть LAZY
    private List<Event> events = new ArrayList<>();

    @OneToOne // fetch default EAGER // Если контакт нужен часто, то можно оставить EAGER. Если редко - то LAZY
    // Если у контакта нет ссылки на студента, то здесь mappedBy не нужен
    private Contact contact;

}
