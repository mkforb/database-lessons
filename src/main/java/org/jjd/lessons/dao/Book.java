package org.jjd.lessons.dao;

/**
 * Created by User on 02.06.2021.
 */
public class Book {
    // В этом классе не должно быть ничего связанного с соединением с БД
    // Это правило проектирования
    // Все запросы, связанные с извлечением/изменением данных, должны находиться в отдельном классе
    // Этот отдельный класс - это DAO-класс
    // Текущий класс - класс-сущность

    // Если предполагается использование рефлексии, то нужно создавать конструктор без параметров,
    // т.к. рефлексия через него создает объект

    private int id;
    private String title;
    private int pageCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
