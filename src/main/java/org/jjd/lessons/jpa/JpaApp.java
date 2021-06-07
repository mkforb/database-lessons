package org.jjd.lessons.jpa;

import org.jjd.lessons.jpa.enity.Article;
import org.jjd.lessons.jpa.enity.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

/**
 * Created by User on 04.06.2021.
 */
public class JpaApp {
    public static void main(String[] args) {
        Message message = new Message();
        message.setText("текст сообщения");
        message.setSent(LocalDateTime.now());

        // EntityManagerFactory создает EntityManager
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("dbConfig");
        // EntityManager управляет сущностями (создает, изменяет, удаляет в БД)
        EntityManager manager = factory.createEntityManager();

        // Добавление в таблицу
        // При добавлении нужно сначала открыть транзакцию begin(), потом подтвердить commit()
        // Данные сохранятся по commit()
        manager.getTransaction().begin();
        manager.persist(message);
        manager.getTransaction().commit();

        Article.ArticleKey key = new Article.ArticleKey();
        key.setName("qwerty");
        key.setSurname("surname");

        Article article = new Article();
        article.setKey(key);
        article.setText("текст статьи");

        manager.getTransaction().begin();
        manager.persist(article);
        manager.getTransaction().commit();

        // Получение по значению первичного ключа
        Article articleFromDb = manager.find(Article.class, key);
        System.out.println(articleFromDb.getKey().getName());

        // Обновление данных
        articleFromDb.setText("Новый текст");
        manager.getTransaction().begin();
        manager.merge(articleFromDb);
        manager.getTransaction().commit();

        System.out.println(manager.find(Article.class, key).getText());

        // Состояние сущностей
        // При создании сущности через new сущность в состоянии new (не под контролем EntityManager)

        // persist(message)
        // Сущность переходит в состояние managed (управляемая менеджером сущностей) через persist() или через find()

        // merge(message)
        // remove(message)

        // close() / clear() - отвяжет сущности, сущность перейдет в состоние detached, менеджер сущностей потеряет контроль над сущностью
        // clear() удаляет сущности из менеджера, менеджер доступен
        // close() закрывает менеджер, он больше недоступен

        // Message message = new Message(); - new
        // persist(message); - managed. Хранит в виде мапы "pk - message"
        // Поскольку persist() запускали в транзакции, в таблице была создана запись в БД
        // Если merge() запустить без транзакции, то сущность изменится во внутр. мапе менеджера сущностей, но не в БД
    }
}
