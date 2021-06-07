package org.jjd.lessons.dao;

import org.jjd.lessons.pool.PoolDataSource;

import java.sql.*;
import java.util.List;

/**
 * Created by User on 02.06.2021.
 */
public class BookDao implements Dao<Book, Integer> {
    // В DAO-классе могут быть методы не только из ИФ
    // Могут быть дополнительные методы для работы с БД

    public void createTable() {
        String createSql = "CREATE TABLE IF NOT EXISTS tb_books (" +
                "id SERIAL PRIMARY KEY, " +
                "title VARCHAR(200) NOT NULL, " +
                "page_count INT NOT NULL" +
                ")";
        // Connection connection = PoolDataSource.getConnection();
        // или
        try (Statement statement = PoolDataSource.getConnection().createStatement()) {
            statement.executeUpdate(createSql);
        } catch (SQLException e) {
            System.out.println("Запрос не был выполнен: " + e.getMessage());
        }
    }

    @Override
    public void add(Book entity) {
        String insertSql = "INSERT INTO tb_books (title, page_count) VALUES (?, ?)";
        try (PreparedStatement statement = PoolDataSource.getConnection().prepareStatement(insertSql)) {
            statement.setString(1, entity.getTitle());
            statement.setInt(2, entity.getPageCount());
            statement.executeUpdate();
            // Получить сгенерированный ключ после создания новой записи
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    entity.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Запрос не был выполнен: " + e.getMessage());
        }
    }

    @Override
    public Book getByPK(Integer id) {
        String sql = "SELECT * FROM tb_books WHERE id = ?";
        Book book = null;
        try (PreparedStatement statement = PoolDataSource.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    book = new Book();
                    book.setId(resultSet.getInt("id"));
                    book.setTitle(resultSet.getString("title"));
                    book.setPageCount(resultSet.getInt("page_count"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Запрос не был выполнен: " + e.getMessage());
        }
        return book;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public void update(Book entity) {
        // Предполагается, что запись в БД существует
        // Обычно фильтрацию делают по значению первичного ключа
        // Иногда методы add(entity) и update(entity) объединяют в один, например, save(entity), в котором проверяют заполнен ли id.
        // Если нет, то выполняется INSERT, иначе - UPDATE
        String update = "UPDATE tb_books SET title = ?, page_count = ? WHERE id = ?";
        try (PreparedStatement statement = PoolDataSource.getConnection().prepareStatement(update)) {
            statement.setString(1, entity.getTitle());
            statement.setInt(2, entity.getPageCount());
            statement.setInt(3, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Запрос не был выполнен: " + e.getMessage());
        }
    }

    @Override
    public void deleteByPK(Integer integer) {

    }
}
