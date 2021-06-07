package org.jjd.lessons.jdbc;

import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by User on 31.05.2021.
 */
public class Examples {
    // Чтобы соединиться с сервером БД нужно 3 вещи:
    // 1. Строка подключения (jdbc:<драйвер_БД>://<адрес_сервера_БД>:<порт_БД>/<имя_БД>)
    private static final String CONNECTION_STR = "jdbc:postgresql://localhost:5432/lessons";
    // 2. Логин пользователя БД
    private static final String USER = "testuser";
    // 3. Пароль пользователя БД
    private static final String PASSWORD = "testuser";

    public static void main(String[] args) {
        createTable();
        /*insertIntoCourse(new Course("Java", 3, 40000));
        insertIntoCourse(new Course("Python", 5, 55000));
        insertIntoCourse(new Course("PHP", 2, 29000));*/
        System.out.println(selectAll());
        System.out.println(selectByPrice(30000));

        HashSet<Course> courses = new HashSet<>();
        courses.add(new Course("Java", 4, 50000));
        courses.add(new Course("JavaScript", 2, 20000));
        courses.add(new Course("NodeJS", 3, 30000));
        // bufferInsert(courses);
    }

    private static void createTable() {
        String createString = "CREATE TABLE IF NOT EXISTS course1 (" +
                "id SERIAL PRIMARY KEY, " +
                "title VARCHAR(255) NOT NULL, " +
                "duration SMALLINT, " +
                "price NUMERIC(9,2)" +
                ")";

        // Регистрация драйвера, загрузка класса
        // Напрямую к классам драйвера не обращаемся. Мы работаем с JDBC, JDBC обращается к драйверу, а драйвер - к БД
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Драйвер не найден");
        }

        // Соединение с БД
        // Открытое соединение обязательно должно быть закрыто
        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, USER, PASSWORD)) {
            // Чтобы выполнить запрос, нужен объект Statement
            // У объекта Statement есть три метода:
            // 1. executeQuery() - если надо получить данные из таблицы
            // 2. executeUpdate() - если надо создать/изменить/удалить таблицу (если вернул 0, то запрос выполнен успешно) или
            // добавить/обновить/удалить данные в таблице (возвращает кол-во измененых/удаленных строк)
            // 3. execute() - возвращает true/false, может использоваться для запросов как executeUpdate()
            try (Statement statement = connection.createStatement()) {
                System.out.println(statement.executeUpdate(createString));
            }
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос " + e.getMessage());
        }
    }

    private static void insertIntoCourse(Course course) {
        // Если данные в запрос приходят из вне, напрямую передавать в запрос нельзя
        // Для выполнения таких запросов используем PrepareStatement
        // Объекты PrepareStatement позволяют защититься от SQL-инъекций. Так же они кешируются, что позволяет улучшить производительность
        // Вместо значений ставится знак "?"
        String insertString = "INSERT INTO course (title, duration, price) VALUES (?, ?, ?)";

        // Регистрация драйвера
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Драйвер не найден");
        }

        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, USER, PASSWORD)) {
            try (PreparedStatement statement = connection.prepareStatement(insertString)) {
                statement.setString(1, course.getTitle()); // 1 - номер параметра в запросе, т.е. номер вопросительного знака в запросе
                statement.setInt(2, course.getDuration());
                statement.setDouble(3, course.getPrice());
                System.out.println(statement.executeUpdate());
            }
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос " + e.getMessage());
        }
    }

    private static HashSet<Course> selectAll() {
        String selectAll = "SELECT * FROM course";
        HashSet<Course> courses = new HashSet<>();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Драйвер не найден");
        }

        // Connection, Statement, ResultSet нужно закрывать после использования
        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, USER, PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                // Результат работы executeQuery() - ResultSet
                try (ResultSet result = statement.executeQuery(selectAll)) {
                    while (result.next()) {
                        int id = result.getInt("id"); // В getInt можно передать номер или имя столбца
                        String title = result.getString("title");
                        int duration = result.getInt("duration");
                        double price = result.getDouble("price");
                        Course course = new Course(title, duration, price);
                        course.setId(id);
                        courses.add(course);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос " + e.getMessage());
        }
        return courses;
    }

    private static HashSet<Course> selectByPrice(double price) {
        // Возвращает курсы, стоимость которых больше указанной
        String selectByPrice = "SELECT * FROM course WHERE price > ?";
        HashSet<Course> courses = new HashSet<>();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Драйвер не найден");
        }

        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, USER, PASSWORD)) {
            try (PreparedStatement statement = connection.prepareStatement(selectByPrice)) {
                statement.setDouble(1, price);
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        Course course = new Course(result.getString("title"),
                                result.getInt("duration"), result.getDouble("price"));
                        course.setId(result.getInt("id"));
                        courses.add(course);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос " + e.getMessage());
        }

        return courses;
    }

    private static void bufferInsert(HashSet<Course> courses) {
        String insertSql = "INSERT INTO course (title, duration, price) VALUES (?, ?, ?)";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Драйвер не найден");
        }

        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, USER, PASSWORD)) {
            try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
                for (Course course : courses) {
                    statement.setString(1, course.getTitle());
                    statement.setInt(2, course.getDuration());
                    statement.setDouble(3, course.getPrice());
                    statement.addBatch(); // Накапливаем запросы
                }
                // executeBatch() - выполнение накопленных запросов
                // Вернет массив с информацией, сколько строк было модифицировано в рамках каждого запроса
                // Если мапа или коллекция с данными, то лучше сначала накопить, а потом все выполнить,
                // чтобы каждый раз не создавать новое соединение с БД,
                // а выполнить все в рамках одного соединения с БД
                System.out.println(Arrays.toString(statement.executeBatch()));
            }
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос " + e.getMessage());
        }
    }
}
