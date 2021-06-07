package org.jjd.lessons.jpa.enity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by User on 02.06.2021.
 */
// Аннотации описаны в JPA
// Если класс - это сущность БД, то указывается @Entity
// имя таблицы = имя класса
@Entity
// @Table позволяет задать имя таблицы. Так же можно указать доп. инф. по индексам и связям с другими сущностями
@Table(name = "tb_messages")
public class Message {
    // В классе сущности должен быть конструктор без параметров, публичные сеттеры и геттеры
    // По умолчанию каждому полю в классе соответствует поле таблицы
    // Транзиентные поля игнорируются (как и Serializable)

    @Getter
    @Setter
    // @Id - поле первичного ключа, несоставной ключ
    // Чтобы значения генерировались авт-ки, надо добавить @GeneratedValue
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Getter
    @Setter
    // @Column - необязательная. Нужна чтобы задать доп. инф. о столбце. Например, название столбца в БД
    // Для строк можно задать длину
    // Для чисел - precision (сколько всего символов), scale (сколько после запятой)
    // nullable - может быть пустым. По умолчанию может
    // insertable
    // updatable
    // unique - создастся индекс, значения по данному столбцу будут уникальными
    // Если столбцы отмечены как индекс, то поиск по ним будет происходить быстрее
    // columnDefinition - описание поля на языке SQL
    @Column(name = "message_text", length = 1000, nullable = false, unique = true)
    private String text;

    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime sent;
}
